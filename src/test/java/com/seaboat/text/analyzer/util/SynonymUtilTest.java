package com.seaboat.text.analyzer.util;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-07-12
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>synonym util tester.</p>
 */
public class SynonymUtilTest {

	@Test
	public void testExtract() {
		SynonymUtil util = SynonymUtil.get("D:\\TextAnalyzer\\src\\main\\resources\\synonym.dic");
		System.out.println(util.getSynonym("标新立异"));
	}
}
