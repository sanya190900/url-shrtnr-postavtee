package edu.kpi.testcourse.bigtable;

import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Custom database.
 */
public class BigTableImpl implements BigTable {

  public static final Map<String, JsonObject> dbUsers = new HashMap<>();
  public static final Map<String, String> dbUrls = new HashMap<>();
  public static final Set<String> dbTokens = new HashSet<>();

  private static Integer idUrl = 1;

  @Override
  public void saveUserInDB(String key, JsonObject value) {
    dbUsers.put(key, value);
  }

  @Override
  public void saveUrlInDB(String key, String value) {
    dbUrls.put(key, value);
    idUrl++;
  }

  @Override
  public void saveToken(String token) {
    dbTokens.add(token);
  }

  @Override
  public Set<String> getTokens() {
    return dbTokens;
  }

  @Override
  public Integer getIdUrlFromDB() {
    return idUrl;
  }

  @Override
  public JsonObject getUserFromDB(String key) {
    return dbUsers.get(key);
  }

  @Override
  public String getUrlFromDB(String key) {
    return dbUrls.get(key);
  }

  @Override
  public void delUrlFromDB(String key) {
    dbUrls.remove(key);
  }
}
