package com.seaboat.text.analyzer.extractor;

import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.extractor.ObjectExtractor;

/**
 * 
 * @author seaboat
 * @date 2018-06-14
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>info extractor tester by crf model.</p>
 */
public class CrfInfoExtractorTest {

	@Test
	public void testExtract() {
		InfoExtractor extractor = new CrfInfoExtractor();
		String text = " 被告人张某某，男，1965年**月**日出生，身份证号码4105261965********，汉族，小学文化，务农，河南省滑县人，户籍所在地河南省xx市滑县**镇**村***号，无前科。因涉嫌危险驾驶罪，于2017年12月28日被滑县公安局刑事拘留。";
		List list = extractor.getIDs(text);
		System.out.println(list);
		list = extractor.getNames(text);
		System.out.println(list);
		list = extractor.getAddrs(text);
		System.out.println(list);
	}
}
