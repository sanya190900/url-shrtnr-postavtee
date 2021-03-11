package edu.kpi.testcourse.rest;

import com.google.gson.JsonObject;
import com.nimbusds.jose.shaded.json.JSONObject;
import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.bigtable.BigTableImpl;
import edu.kpi.testcourse.logic.UrlAndUserActions;
import edu.kpi.testcourse.model.Url;
import edu.kpi.testcourse.model.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.security.Principal;
import java.util.ArrayList;

/**
 * Controller for URLs.
 */
@Controller("/urls")
public class UrlsController {

  record UrlListClass(ArrayList<JsonObject> urls) {}

  /**
   * Method to implement аauthorization.
   *
   * @param token authorization token.
   * @param object long url in JSON format.
   * @param principal user object.
   * @return response code.
   */
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Post(value = "/shorten",
    consumes = MediaType.APPLICATION_JSON,
    produces = MediaType.APPLICATION_JSON)
  public MutableHttpResponse<String> shortenUrl(@Header("Authorization") String token,
                                                @Body JSONObject object, Principal principal) {
    if (BigTableImpl.dbTokens.contains(token.replaceFirst("Bearer[ ]+", ""))) {
      JsonObject urlJson = Main.getGson().fromJson(object.toJSONString(), JsonObject.class);

      if (!urlJson.has("url")) {
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("reason_code", 0);
        jsonResponse.addProperty("reason_text", "Cannot parse json");
        return HttpResponse.badRequest(jsonResponse.toString()).contentType("application/json");
      }

      Url url = new Url(urlJson.get("url").getAsString());
      url.setShortUrl(Url.toShort(Main.bigTable.getIdUrlFromDb()));
      UrlAndUserActions.saveUrl(url.getShortUrl(), url.getLongUrl());
      User user = UrlAndUserActions.findUserByEmail(principal.getName());
      if (user == null) {
        return HttpResponse.unauthorized();
      }

      user.addInUrlList(url.getShortUrl());
      UrlAndUserActions.updUserInfo(user);
      JsonObject jsonResponse = new JsonObject();
      jsonResponse.addProperty("shortened_url", url.getShortUrl());
      return HttpResponse.created(jsonResponse.toString()).contentType("application/json");
    } else {
      return HttpResponse.unauthorized();
    }
  }

  /**
   * Method to implement saved urls deletion.
   *
   * @param token authorization token.
   * @param shortUrl short url.
   * @param principal user object.
   * @return response code.
   */
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Delete(value = "/{shortUrl}",
    consumes = MediaType.APPLICATION_JSON,
    produces = MediaType.APPLICATION_JSON)
  public MutableHttpResponse<String> deleteUrl(@Header("Authorization") String token,
                                               @Body String shortUrl, Principal principal) {
    if (BigTableImpl.dbTokens.contains(token.split(" ")[1])) {
      String fullUrl = Main.bigTable.getUrlFromDb(shortUrl);
      User user = UrlAndUserActions.findUserByEmail(principal.getName());

      if (fullUrl == null) {
        return HttpResponse.notFound("Such short url '" + shortUrl
          + "' no longer exists");
      }
      if (user == null) {
        return HttpResponse.unauthorized();
      }

      UrlAndUserActions.deleteUrl(shortUrl);
      user.removeFromUrlList(shortUrl);
      return HttpResponse.noContent();
    } else {
      return HttpResponse.unauthorized();
    }
  }

  /**
   * Method to get list of shortened urls of user.
   *
   * @param token authorization token.
   * @param principal user object.
   * @return list of urls.
   */
  @Secured(SecurityRule.IS_AUTHENTICATED)
  @Get(value = "/",
    consumes = MediaType.APPLICATION_JSON,
    produces = MediaType.APPLICATION_JSON)
  public MutableHttpResponse<String> listUrls(@Header("Authorization") String token,
                                              Principal principal) {
    if (BigTableImpl.dbTokens.contains(token.split(" ")[1])) {
      User user = UrlAndUserActions.findUserByEmail(principal.getName());

      if (user == null) {
        return HttpResponse.unauthorized();
      }

      ArrayList<String> shortUrls = user.getUrlList();
      ArrayList<JsonObject> urlList = UrlAndUserActions.listOfUrls(shortUrls);
      return HttpResponse.ok(Main.getGson().toJson(new UrlListClass(urlList)));
    } else {
      return HttpResponse.unauthorized();
    }
  }
}

