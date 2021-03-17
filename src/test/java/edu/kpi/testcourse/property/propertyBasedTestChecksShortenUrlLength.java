package edu.kpi.testcourse.property;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.integers;
import static org.quicktheories.generators.SourceDSL.strings;

import edu.kpi.testcourse.logic.UrlAndUserActions;
import edu.kpi.testcourse.model.Url;
import org.junit.jupiter.api.Test;

public class propertyBasedTestChecksShortenUrlLength {

  /**
   * Test checks that length of shorten Url should be less than length of full Url
   */
  @Test
  void shortenUrlShouldBeShorterThanFull_propertyBased(){
    qt()
      .withExamples(100)
      .forAll(
        strings().basicLatinAlphabet().ofLengthBetween(15, 20),
        integers().between(1, 101)
      ).check((fullUrl, id) -> {
      String shortUrl = Url.toShort(id);
      UrlAndUserActions.saveUrl(shortUrl, fullUrl);
      return shortUrl.length() < fullUrl.length();
    });
  }
}
