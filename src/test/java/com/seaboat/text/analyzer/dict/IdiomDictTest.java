package com.seaboat.text.analyzer.dict;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-07-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>core dict tester.</p>
 */
public class IdiomDictTest {

	@Test
	public void test() {
			System.out.println(IdiomDict.get().searchIdiom("从前有个人阿谀奉承"));
	}

}
