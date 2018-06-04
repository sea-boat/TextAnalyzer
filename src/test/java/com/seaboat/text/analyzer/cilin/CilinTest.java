package com.seaboat.text.analyzer.cilin;

import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-04
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>cilin test.</p>
 */
public class CilinTest {

	@Test
	public void test() {
		CilinDictionary dict = CilinDictionary.getInstance();
		Set<String> code = dict.getCilinCoding("人类");
		System.out.println(code);
		System.out.println(dict.getCilinWords(code.iterator().next()));
	}

}
