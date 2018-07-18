package com.seaboat.text.analyzer.tagging;

import java.io.IOException;
import java.util.List;

import com.seaboat.text.analyzer.exception.SizeException;
import com.seaboat.text.analyzer.ml.hmm.HMMModel;
import com.seaboat.text.analyzer.ml.hmm.ViterbiDecoder;
import com.seaboat.text.analyzer.util.CorpusUtil;

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
		String text = "申诉人 朝阳市 常平镇 大和百货 中国移动";

		String[] words = text.split(" ");

		HMMModel model = new HMMModel();
		List<String[]>[] lists = CorpusUtil.readPeopleDailyCorpus("./data/corpus_data/train.txt");
		try {
			model.train(lists[0], lists[1]);
		} catch (SizeException e) {
			e.printStackTrace();
		}
		ViterbiDecoder decoder = new ViterbiDecoder(model);
		decoder.decode(words);
		for (String s : words)
			System.out.println(s);
	}
}
