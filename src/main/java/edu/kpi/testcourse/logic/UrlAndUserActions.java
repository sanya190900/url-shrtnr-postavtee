package edu.kpi.testcourse.logic;

import com.google.gson.JsonObject;
import edu.kpi.testcourse.Main;
import edu.kpi.testcourse.bigtable.BigTableImpl;
import edu.kpi.testcourse.model.Url;
import edu.kpi.testcourse.model.User;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Клас в якому реалізовано основні дії користувача.
 */
public class UrlAndUserActions {

  /**
   * Створення користувача і додавання його в ДБ.
   *
   * @param u користувач.
   * @return результат створення.
   */
  public static boolean createUser(User u) {
    u.setPassw(hash(u.getPassw()));
    u.createUrlList();
    if (BigTableImpl.dbUsers.isEmpty()) {
      BigTableImpl.dbUsers.put(u.getIdUser(), u.toJson());
      return true;
    } else {
      boolean find = false;
      for (Map.Entry<String, JsonObject> entry : BigTableImpl.dbUsers.entrySet()) {
        if ((u.getEmail().equals(Main.getGson().fromJson(entry.getValue().toString(), User.class)
            .getEmail()))) {
          find = true;
          break;
        }
      }
      if (!find) {
        BigTableImpl.dbUsers.put(u.getIdUser(), u.toJson());
        return true;
      }
    }
    return false;
  }

  /**
   * Функція для оновлення інформації про користувача.
   *
   * @param u користувач.
   */
  public static void updUserInfo(User u) {
    BigTableImpl.dbUsers.put(u.getIdUser(), u.toJson());
  }

  /**
   * Функція для хешування паролю користувача.
   *
   * @param passw пароль.
   * @return hash.
   */
  public static String hash(String passw) {
    String hash = "";
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = messageDigest.digest(passw.getBytes(StandardCharsets.UTF_8));
      BigInteger noHash = new BigInteger(1, hashBytes);
      hash = noHash.toString(16);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hash;
  }

  /**
   * Пошук і повернення користувача по емейлу в ДБ.
   *
   * @param email емейл користувача.
   * @return результат пошуку.
   */
  public static User findUserByEmail(String email) {
    for (Map.Entry<String, JsonObject> entry : BigTableImpl.dbUsers.entrySet()) {
      if (email.equals(Main.getGson().fromJson(entry.getValue(), User.class).getEmail())) {
        User user = Main.getGson().fromJson(entry.getValue(), User.class);
        user.setIdUser(entry.getKey());
        return user;
      }
    }
    return null;
  }

  /**
   * Перевірка пароля користувача.
   *
   * @param emailFromRequest емейл який передається.
   * @param passwFromRequest пароль який передається.
   * @return результ перевірки.
   */
  public static boolean checkPassw(String emailFromRequest, String passwFromRequest) {
    for (Map.Entry<String, JsonObject> entry : BigTableImpl.dbUsers.entrySet()) {
      if ((emailFromRequest.equals(Main.getGson().fromJson(entry.getValue(), User.class)
          .getEmail()))
          && (hash(passwFromRequest).equals(Main.getGson().fromJson(entry.getValue(), User.class)
          .getPassw()))) {
        return true;
      }
    }

    return false;
  }

  /**
   * Створення посилання.
   *
   * @param shortUrl скорочене посилання.
   * @param longUrl  довге посилання.
   */
  public static void saveUrl(String shortUrl, String longUrl) {
    Main.bigTable.saveUrlInDb(shortUrl, longUrl);
  }

  /**
   * Видалення посилання з ДБ.
   *
   * @param longUrl посилання задане користувачем.
   */
  public static void deleteUrl(String longUrl) {
    Main.bigTable.delUrlFromDb(longUrl);
  }

  /**
   * Список посилань коритстувача.
   *
   * @param listOfShortUrls список скрочених посилань.
   * @return allUrlList об'єкт JSON.
   */
  public static ArrayList<JsonObject> listOfUrls(ArrayList<String> listOfShortUrls) {
    ArrayList<JsonObject> allUrlList = new ArrayList<>();

    for (String shortUrl : listOfShortUrls) {
      Url url = new Url(BigTableImpl.dbUrls.get(shortUrl));
      url.setShortUrl(shortUrl);
      allUrlList.add(url.toJson());
    }
    return allUrlList;
  }

  /**
   * Відновлення довгого посилання із скороченого.
   *
   * @param shortUrl скорочене посилання
   * @return longUrl
   */
  public static String convertToLong(String shortUrl) {
    return BigTableImpl.dbUrls.get(shortUrl);
  }
}
