package edu.kpi.testcourse.bigtable;

import edu.kpi.testcourse.logic.UrlConversion;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BigTableImplTest {

  @Test
  void checkValueSaving() {
    BigTableImpl bigTable = new BigTableImpl();

    bigTable.putUser("testKey", object);
    JsonObject = bigTable.getUser("testKey");

    assertThat(value).isEqualTo(object);
  }

  @Test
  void checkToShort() {
    long value = 1000;
    UrlConversion urlConversion = new UrlConversion();

    assertThat(urlConversion.toShort(value)).isEqualTo("g8");
  }

  @Test
  void checkToLong() {
    String value = "g8";
    UrlConversion urlConversion = new UrlConversion();

    assertThat(urlConversion.toLong(value)).isEqualTo(1000);
  }

  @Test
  void checkUrlConvertToShort() {
    // для функції convertToShort() в класі UrlLogic.
  }

  @Test
  void checkUrlConvertToLong() {
    // для функції convertToLong() в класі UrlLogic.
  }

}
