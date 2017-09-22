package com.seaboat.text.analyzer.tagging;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.seaboat.text.analyzer.util.StringUtil;

/**
 * 
 * @author seaboat
 * @date 2017-09-22
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hmm model tester.</p>
 */
public class HMMModelTester {

  public static void main(String[] args) throws IOException {
    String text =
        "申诉人于2014年10月7日在朝阳市常平镇大和百货1楼的中国移动购买了品牌是苹果5S和唯米2部手机，申诉人反映两部手机无故出现弯曲的情况。申诉人于12月11日与商家沟通，商家回复将手机返厂家检测，并通知申诉人于12月12日到商店了解检测结果。申诉人于12月12日联系商家了解结果，商家口头回复申诉人表示出故此情况属于人为因素，对此申诉人不接受要求商家提供检测报告遭商家拒绝，现申诉人要求商家出具书面检测报告，申诉人称无法提供投诉方的联系方式，请协调。";

    Analyzer anal = new IKAnalyzer(true);
    StringReader reader = new StringReader(text);
    TokenStream ts = anal.tokenStream("", reader);
    ts.reset();
    CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
    List<String> list = new ArrayList<String>();
    while (ts.incrementToken()) {
      list.add(term.toString());
    }
    anal.close();
    ts.close();
    reader.close();

    HMMModel model = new HMMModel();
    model.train();
    ViterbiDecoder decoder = new ViterbiDecoder(model);
    List<String> listF = new ArrayList<String>();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).length() == 1) continue;
      if (StringUtil.hasDigit(list.get(i))) continue;
      if (list.get(i).equals("br")) continue;
      listF.add(list.get(i));
    }
    String[] words = new String[listF.size()];
    listF.toArray(words);
    decoder.decode(words);
    for (String s : words)
      System.out.println(s);
  }
}
