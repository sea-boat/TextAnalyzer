package com.seaboat.text.analyzer.util;

import java.util.List;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-07-18
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>corpus util tester.</p>
 */
public class CorpusUtilTest {

	@Test
	public void test() {
		String file = "./data/corpus_data/train.txt";
		List<String[]>[] lists = CorpusUtil.readPeopleDailyCorpus(file);
		System.out.println(lists[0].size());
		System.out.println(lists[1].size());
	}
}
