package com.seaboat.text.analyzer.dict;

import java.util.List;

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
public class CoreDictTest {

	@Test
	public void createAndDeleteTree() {
		List<Integer> list = CoreWordDict.get().prefixSearch("从前有个人");
		for(int i:list)
			System.out.println(CoreWordDict.get().getStringByIndex(i));
	}

}
