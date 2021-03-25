package edu.kpi.testcourse.property;

import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.SourceDSL.strings;

import edu.kpi.testcourse.bigtable.BigTableImpl;
import edu.kpi.testcourse.logic.UrlAndUserActions;
import edu.kpi.testcourse.model.User;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class propertyBasedTestForCreatingUsers {
  /**
   * Test checks that after creating random user we can find him so everything works fine
   */
  @Test
  void shouldCreateUser_propertyBased(){
    qt()
      .withExamples(100)
      .forAll(
        strings().basicLatinAlphabet().ofLengthBetween(7, 20),
        strings().basicLatinAlphabet().ofLengthBetween(8, 16)
      ).check((email, password) -> {
      email = beautifulEmail(email);
      User user = new User(email, password, new ArrayList<>());
      user.setIdUser(UUID.randomUUID().toString());
      UrlAndUserActions.createUser(user);
      return UrlAndUserActions.findUserByEmail(email) != null;
    });
  }

  private String beautifulEmail(String email){
    return email + "@gmail.com";
  }
}

