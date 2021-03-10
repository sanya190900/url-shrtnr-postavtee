package edu.kpi.testcourse.bigtable;

import com.google.gson.JsonObject;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import java.util.Set;

/**
 * BigTable is a key-value storage...
 */
public interface BigTable {
  void saveUserInDb(String key, JsonObject value);

  void saveUrlInDb(String key, String value);

  void saveToken(String token);

  Set<String> getTokens();

  Integer getIdUrlFromDb();

  JsonObject getUserFromDb(String key);

  String getUrlFromDb(String key);

  void delUrlFromDb(String key);
}
