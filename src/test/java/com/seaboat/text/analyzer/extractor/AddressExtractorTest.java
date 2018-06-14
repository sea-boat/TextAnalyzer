package com.seaboat.text.analyzer.extractor;

import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.extractor.AddressExtractor;

/**
 * 
 * @author seaboat
 * @date 2017-09-19
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>address extractor tester.</p>
 */
public class AddressExtractorTest {

  @Test
  public void testExtract() {
    String str =
        "申诉人在虎门镇南栅五区千惠百货1楼的中国联通千惠专营店以分期付款的形式购买了一部苹果5S的手机，价值4198元，商家向申诉人称交500元可以办理分期付款，申诉人反映交了500元的首付，并持有收据，收据上写明是首付500元预存机，申诉人交费后商家没有为申诉人提供手机，商家还要求申诉人再交1000元才可以办理分期付款，申诉人向商家表示因个人因素不能交1000元，申诉人要求商家提供手机或者退换500元遭商家拒绝，现申诉人要求商家提供手机或者退还500元，请协调。";
    AddressExtractor extractor = new AddressExtractor();
    List<String> list = extractor.extract(str);
    for (String s : list)
      System.out.println(s);
  }
}
