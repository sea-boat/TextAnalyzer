package com.seaboat.text.training;

public enum Labels {

  ENVIRONMENT("环保", 1), TRAFFIC("交通", 2), PHONE("手机", 3), CONSUMPTION("日常消费", 4), LAW("法律", 5), CAR(
      "汽车", 6), EDUCATION("教育", 7), SOCIAL("社保", 8), GOVERNMENT("政府", 9), HOSPITAL("医院", 10), HOUSE(
      "房产", 11), COMPUTER("电脑", 12);

  private String name;
  private int id;

  private Labels(String name, int id) {
    this.name = name;
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }
  
}
