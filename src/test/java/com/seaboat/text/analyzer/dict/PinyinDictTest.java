package com.seaboat.text.analyzer.dict;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-08-14
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>pinyin dict tester.</p>
 */
public class PinyinDictTest {

	@Test
	public void test() {
		System.out.println(PinyinDict.get().getStringByIndex(PinyinDict.get().exactlySearch("一心一意")));
	}

}
