package edu.kpi.testcourse.bigtable;

import com.google.gson.JsonObject;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import java.util.Set;

/**
 * BigTable is a key-value storage...
 */
public interface BigTable {
  void saveUserInDB(String key, JsonObject value);

  void saveUrlInDB(String key, String value);

  void saveToken(String token);

  Set<String> getTokens();

  Integer getIdUrlFromDB();

  JsonObject getUserFromDB(String key);

  String getUrlFromDB(String key);

  void delUrlFromDB(String key);
}
