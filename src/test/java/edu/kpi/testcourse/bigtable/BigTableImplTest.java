package edu.kpi.testcourse.bigtable;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
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
}
