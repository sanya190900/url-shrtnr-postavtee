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
   * Функція яка перетворює довге посилання в коротке.
   *
   * @param input - довге посилання яке дає користувач.
   * @return result - короткий лінк.
   */
  public String toShort(long input) {
    var encodedString = new StringBuilder();
    var result = new String();

    if (input == 0) {
      return String.valueOf(allowedCharacters[0]);
    }

    while (input > 0) {
      encodedString.append(allowedCharacters[(int) (input % base)]);
      input = input / base;
    }

    result = encodedString.reverse().toString();

    return result;
  }

  /**
   * Функція яка перетворює коротке посилання на довге.
   *
   * @param input - коротке посилання яке потрібно збільшити.
   * @return longUrl - довгий лінк.
   */
  public long toLong(String input) {
    var characters = input.toCharArray();
    var length = characters.length;
    var longUrl = 0;

    //counter is used to avoid reversing input string
    var counter = 1;
    for (int i = 0; i < length; i++) {
      longUrl += allowedString.indexOf(characters[i]) * Math.pow(base, length - counter);
      counter++;
    }
    return longUrl;
  }
}
