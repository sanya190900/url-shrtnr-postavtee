package edu.kpi.testcourse.logic;

import edu.kpi.testcourse.model.Url;

/**
 * Клас в якому реалізована основна логіка конвертування отриманого посилання.
 */
public class UrlLogic {
  private final UrlConversion conversion;

  public UrlLogic(UrlConversion urlConversion) {
    this.conversion = urlConversion;
  }

  /**
   * Функція яка перетворює довге посилання в коротке.
   *
   * @return - короткий лінк.
   */
  public String convertToShort() {
    //  у функцію має передаватись запит з довгим посиланням.
    Url url = new Url();
    //  url.setCreateDate(); тут має бути встановлена дата створення посилання.
    //  url.setExpireDate(); тут має бути встановлена дата, коли посилання згасне.
    //  url.setLongUrl(); тут у функцію має передатись ссилка яку вводить користувач.
    //  url.setIdUrl(); тут для ссилки має встановитись унікальний ідентифікатор.
    //  тут цей об'єкт має бути переданий до сховища даних.

    return conversion.toShort(url.getIdUrl()); // тут через ІД створюється аліас дляя короткої урли.
  }

  /**
   * Функція яка перетворює довге посилання в коротке.
   *
   * @param shortUrl - довге посилання яке дає користувач.
   * @return result - короткий лінк.
   */
  public String convertToLong(String shortUrl) {
    // в цю функцію має передаватись аліас, який перетвориться в ІД.
    // Або ціла урла, в якій треба буде відокремити аліас.
    var id = conversion.toLong(shortUrl);
    var url = new Url(); // тут має бути функція пошуку по ід.
    //  має бути перевірка на помилку, якщо посилання не знайдено.
    //  далі має бути перевірка, чи в посилання не закінчився термін дії.
    //  якщо термін дії вичерпано, то посилання видаляється із сховища.
    //  і видається повідомлення про помилку, що посилання більше не дійсне.

    return url.getLongUrl();
  }
}
