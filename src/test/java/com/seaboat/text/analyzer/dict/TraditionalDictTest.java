package com.seaboat.text.analyzer.dict;

import java.util.List;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-08-08
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>traditional dict tester.</p>
 */
public class TraditionalDictTest {

	@Test
	public void test() {
		List<Integer> list = TraditionalDict.get().prefixSearch("1隻大狗");
		for(int i:list)
			System.out.println(TraditionalDict.get().getStringByIndex(i));
	}

}
