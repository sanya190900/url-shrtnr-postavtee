package edu.kpi.testcourse.logic;

import java.util.Date;

/**
 * Об'єкт посилання.
 */
public class Url {
  private long idUrl;
  private String longUrl;
  private Date createDate;
  private Date expireDate;

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
   * Метод який повертає дату створення посилання.
   *
   * @return createDate - дата створення.
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * Метод який повертає дату створення посилання.
   *
   * @return createDate - дата створення.
   */
  public Date getExpireDate() {
    return expireDate;
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
   * Метод який встановлює дату створення посилання.
   *
   * @param createDate - дата створення.
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * Метод який встановлює дату згасання посилання.
   *
   * @param expireDate - дата згасання.
   */
  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }
}
