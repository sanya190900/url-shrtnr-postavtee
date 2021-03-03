package edu.kpi.testcourse.logic;

/**
 * Клас який реалізує логіку скорочення посилання.
 */

public class UrlConversion {

  private static final String allowedString = "0123456789abcdefghijklmnopqrstuvwxyz"
      + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private char[] allowedCharacters = allowedString.toCharArray();
  private int base = allowedCharacters.length;

  /**
   * Функція яка створює короткий аліас.
   *
   * @param input - довге посилання яке дає користувач.
   * @return - короткий аліас.
   */
  public String toShort(long input) {
    var encodedString = new StringBuilder();

    if (input == 0) {
      return String.valueOf(allowedCharacters[0]);
    }

    while (input > 0) {
      encodedString.append(allowedCharacters[(int) (input % base)]);
      input = input / base;
    }

    return encodedString.reverse().toString();
  }

  /**
   * Функція яка повертає унікальний ІД посилання.
   *
   * @param input - короткий аліас який потрібно збільшити.
   * @return idUrl - айді посилання.
   */
  public long toLong(String input) {
    var characters = input.toCharArray();
    var length = characters.length;
    var idUrl = 0;

    //counter is used to avoid reversing input string
    var counter = 1;
    for (int i = 0; i < length; i++) {
      idUrl += allowedString.indexOf(characters[i]) * Math.pow(base, length - counter);
      counter++;
    }
    return idUrl;
  }
}
