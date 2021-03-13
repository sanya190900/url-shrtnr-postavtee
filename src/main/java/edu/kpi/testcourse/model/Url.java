package edu.kpi.testcourse.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.kpi.testcourse.Main;

/**
 * Об'єкт посилання.
 */
public class Url {
  private Integer idUrl;
  private String longUrl;
  private String shortUrl;
  private static final String allowedString = "0123456789abcdefghijklmnopqrstuvwxyz"
      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static char[] allowedCharacters = allowedString.toCharArray();
  private static int base = allowedCharacters.length;

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
  public Integer getIdUrl() {
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
  public void setIdUrl(Integer idUrl) {
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
   */
  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
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
   * @param idUrl - довге ідентифікатор посилання.
   * @return - короткий аліас.
   */
  public static String toShort(Integer idUrl) {
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
