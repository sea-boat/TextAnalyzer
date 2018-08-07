package com.seaboat.text.analyzer.dict;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-08-07
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>organization dict tester.</p>
 */
public class OrganizationDictTest {

	@Test
	public void test() {
		System.out.println(OrganizationDict.get().searchOrganization("去阿里巴巴找朋友"));
	}

}
