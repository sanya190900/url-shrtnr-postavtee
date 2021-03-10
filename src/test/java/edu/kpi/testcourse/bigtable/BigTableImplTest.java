package edu.kpi.testcourse.bigtable;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import edu.kpi.testcourse.logic.UrlConversion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BigTableImplTest {

  @Test
  void checkValueSaving() {
    BigTableImpl bigTable = new BigTableImpl();

    /* Adding props to user object */
    JsonObject userObject = new JsonObject();
    userObject.addProperty("email", "test@mail.com");
    userObject.addProperty("password", "testPassword");
    userObject.add("userLinks", new JsonArray());

    /* Saving and getting user from db */
    bigTable.saveUserInDb("testKey", userObject);
    JsonObject getUser = bigTable.getUserFromDb("testKey");

    /* Comparing saved user and user that we get from the db */
    assertThat(getUser).isEqualTo(userObject);
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
