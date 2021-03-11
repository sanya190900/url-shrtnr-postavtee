package edu.kpi.testcourse.rest;

import edu.kpi.testcourse.logic.UrlAndUserActions;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST API controller that provides logic for Micronaut framework.
 */
@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
public class RedirectController {
  record ExampleClass(String first, String second) {}

  /**
   * Method to implement request for redirecting from short url to long url.
   *
   * @param shortUrl - short url
   * @return response code
   */
  @Get(value = "/r/{shortUrl}", produces = MediaType.APPLICATION_JSON)
  public MutableHttpResponse<String> redirect(@Body String shortUrl) {
    String fullUrl = UrlAndUserActions.convertToLong(shortUrl);

    if (fullUrl == null) {
      return HttpResponse.notFound("Can't found short url '" + shortUrl + "'");
    }

    try {
      URI location = new URI(fullUrl);
      return HttpResponse.redirect(location);
    } catch (URISyntaxException e1) {
      e1.printStackTrace();
      return HttpResponse.badRequest();
    }
  }
}
