package com.seaboat.text.analyzer.dict;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-07-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>name dict tester.</p>
 */
public class NameDictTest {

	@Test
	public void test() throws Exception {
		System.out.println(NameDict.get().searchName("汪建是华大基因董事长"));
		System.out.println(NameDict.get().searchEnglishName("Tom and Jim are my friends"));
	}

}
