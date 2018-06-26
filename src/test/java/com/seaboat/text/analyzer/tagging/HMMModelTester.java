package com.seaboat.text.analyzer.tagging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import com.seaboat.text.analyzer.ml.hmm.HMMModel;
import com.seaboat.text.analyzer.ml.hmm.ViterbiDecoder;
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

    List<Term> termList = ToAnalysis.parse(text).getTerms();
	List<String> wordList = new ArrayList<String>();
	for (Term wordTerm : termList) {
		wordList.add(wordTerm.getName());
	}

    HMMModel model = new HMMModel();
    model.train();
    ViterbiDecoder decoder = new ViterbiDecoder(model);
    List<String> listF = new ArrayList<String>();
    for (int i = 0; i < wordList.size(); i++) {
      if (wordList.get(i).length() == 1) continue;
      if (StringUtil.hasDigit(wordList.get(i))) continue;
      if (wordList.get(i).equals("br")) continue;
      listF.add(wordList.get(i));
    }
    String[] words = new String[listF.size()];
    listF.toArray(words);
    decoder.decode(words);
    for (String s : words)
      System.out.println(s);
  }
}
