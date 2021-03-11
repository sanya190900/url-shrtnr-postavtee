package edu.kpi.testcourse.rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.shaded.json.JSONObject;
import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.bigtable.BigTableImpl;
import edu.kpi.testcourse.logic.UrlAndUserActions;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

/**
 * Controller for accessing user's account.
 */
@Controller("/users")
public class UsersController {
  @Inject
  @Client("/")
  RxHttpClient client;

  /**
   * Method with request for signing up.
   *
   *
   */
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Post(value = "/signup",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
  public HttpResponse signUp(@Body JSONObject object) {
    User user = Main.getGson().fromJson(object.toJSONString(), User.class);
    if (user.getPassw() == null) {
      return HttpResponse.badRequest("No password specified");
    }
    if (user.getEmail() == null) {
      return HttpResponse.badRequest("No email specified");
    }
    user.setIdUser(UUID.randomUUID().toString());
    boolean response = UrlAndUserActions.createUser(user);
    if (response) {
      return HttpResponse.created("User with email \"" + user.getEmail() + "\" was created.");
    } else {
      return HttpResponse.badRequest("User with email \"" + user.getEmail() + "\" already exists.");
    }
  }

  /**
   * Method with request for signing in.
  */
  @Secured(SecurityRule.IS_ANONYMOUS)
  @Post(value = "/signin",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
  public HttpResponse signIn(@Body JSONObject object) {
    User user = Main.getGson().fromJson(object.toJSONString(), User.class);
    if (user.getEmail() == null) {
      return HttpResponse.badRequest("Please, provide a valid email");
    }
    if (user.getPassw() == null) {
      return HttpResponse.badRequest("Please, provide a valid password");
    }

    for (Map.Entry<String, JsonObject> entry : BigTableImpl.dbUsers.entrySet()) {
      if ((user.getEmail()
          .equals(Main.getGson().fromJson(entry.getValue(), User.class).getEmail()))) {
        user.setIdUser(entry.getKey());
        break;
      }
    }
    UsernamePasswordCredentials credentials =
        new UsernamePasswordCredentials(user.getEmail(), user.getPassw());

    HttpResponse<String> httpResponse;

    User response = UrlAndUserActions.findUserByEmail(user.getEmail());
    if (response != null) {
      boolean isPasswordValid = UrlAndUserActions.checkPassw(user.getEmail(), user.getPassw());
      if (isPasswordValid) {
        HttpRequest request = HttpRequest.POST("/login", credentials);
        httpResponse = client.toBlocking().exchange(request, String.class);
      } else {
        return HttpResponse.badRequest("Wrong password, try again");
      }
    } else {
      return HttpResponse.notFound("User with email \"" + user.getEmail() + "\" was not found.");
    }
    JsonObject jsonObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
    BigTableImpl.dbTokens.add(jsonObject.get("access_token").getAsString());
    return httpResponse;
  }

  /**
   * Method with request for signing out.
   *
   *
   */
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Post(value = "/signout", consumes = MediaType.APPLICATION_JSON)
  public HttpResponse signOut(@Header("Authorization") String token) {
    if (BigTableImpl.dbTokens.contains(token.split(" ")[1])) {
      token = token.split(" ")[1];
      BigTableImpl.dbTokens.remove(token);
      return HttpResponse.noContent();
    } else {
      return HttpResponse.unauthorized();
    }
  }

}


