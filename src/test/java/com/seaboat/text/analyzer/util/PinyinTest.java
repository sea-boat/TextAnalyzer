package com.seaboat.text.analyzer.util;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>pinyin tester.</p>
 */
public class PinyinTest {

	@Test
	public void testExtract() {
		System.out.println(PinyinUtil.getInstance().getPinyin("哈哈"));
		System.out.println(PinyinUtil.getInstance().getPinyin("中"));
		System.out.println(PinyinUtil.getInstance().getPinyin("中国"));
	}
}
