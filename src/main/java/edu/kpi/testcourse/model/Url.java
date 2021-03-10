package edu.kpi.testcourse.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.kpi.testcourse.Main;

/**
 * Об'єкт посилання.
 */
public class Url {
  private long idUrl;
  private String longUrl;
  private String shortUrl;
  private static final String allowedString = "0123456789abcdefghijklmnopqrstuvwxyz"
      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private char[] allowedCharacters = allowedString.toCharArray();
  private int base = allowedCharacters.length;

  /**
   * Конструктор для посилання.
   */
  public Url(String longUrl) {
    this.longUrl = longUrl;
  }

  /**
   * Метод який повертає унікальний номер посилання.
   *
   * @return idUrl - унікальний номер посилання.
   */
  public long getIdUrl() {
    return idUrl;
  }

  /**
   * Метод який повертає довге посилання.
   *
   * @return longUrl - довге посилання.
   */
  public String getLongUrl() {
    return longUrl;
  }

  /**
   * Метод який повертає коротке посилання.
   *
   * @return longUrl - довге посилання.
   */
  public String getShortUrl() {
    return shortUrl;
  }

  /**
   * Метод який встановлює значеня унікального номеру.
   *
   * @param idUrl - унікальний номер.
   */
  public void setIdUrl(long idUrl) {
    this.idUrl = idUrl;
  }

  /**
   * Метод який встановлює значеня довгого посилання.
   *
   * @param longUrl - довге посилання.
   */
  public void setLongUrl(String longUrl) {
    this.longUrl = longUrl;
  }

  /**
   * Метод який встановлює значеня короткого посилання.
   *
   * @param longUrl - довге посилання.
   */
  public void setShortUrl(String longUrl) {
    this.longUrl = longUrl;
  }

  /**
   * Метод для конвертування посилання в JSON.
   *
   * @return  об'єкт в форматі JSON.
   */
  public JsonObject toJson() {
    String json =  Main.getGson().toJson(this, Url.class);
    return JsonParser.parseString(json).getAsJsonObject();
  }

  /**
   * Функція яка створює короткий аліас.
   *
   * @param idUrl - довге посилання яке дає користувач.
   * @return - короткий аліас.
   */
  public String toShort(long idUrl) {
    var encodedString = new StringBuilder();

    if (idUrl == 0) {
      return String.valueOf(allowedCharacters[0]);
    }

    while (idUrl > 0) {
      encodedString.append(allowedCharacters[(int) (idUrl % base)]);
      idUrl = idUrl / base;
    }

    return encodedString.reverse().toString();
  }
}
