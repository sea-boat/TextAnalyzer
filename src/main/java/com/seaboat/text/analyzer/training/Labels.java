package com.seaboat.text.analyzer.training;

/**
 * 
 * @author seaboat
 * @date 2017-06-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>labels for all text classes.</p>
 */
public enum Labels {

  ENVIRONMENT("环保", 0), TRAFFIC("交通", 1), PHONE("手机", 2), CONSUMPTION("日常消费", 3), LAW("法律", 4), CAR(
      "汽车", 5), EDUCATION("教育", 6), SOCIAL("社保", 7), GOVERNMENT("政府", 8), HOSPITAL("医院", 9), HOUSE(
      "房产", 10), COMPUTER("电脑", 11);

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
