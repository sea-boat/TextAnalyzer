package com.seaboat.text.analyzer.tagging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-05-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hmm predictor tester.</p>
 */
public class HMMPredictorTester {

	public static void main(String[] args) throws IOException {
		List<String> list = new ArrayList<String>();
		list.add("金正恩");
		list.add("强调");
		list.add("要");
		list.add("彻底");
		list.add("建立");
		list.add("体系");
		HMMPredictor predictor = HMMPredictor.getIntance();
		String[] words = predictor.predict(list);
		for (String s : words)
			System.out.println(s);
	}
}
