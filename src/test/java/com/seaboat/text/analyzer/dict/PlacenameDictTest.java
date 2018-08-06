package com.seaboat.text.analyzer.dict;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-08-06
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>place name dict tester.</p>
 */
public class PlacenameDictTest {

	@Test
	public void test() {
		System.out.println(PlacenameDict.get().searchPlacename("我住在天河北路，不在广州大道中，在天河区"));
	}

}
