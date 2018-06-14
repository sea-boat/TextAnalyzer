package com.seaboat.text.analyzer.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.seaboat.text.analyzer.IObjectExtractor;

/**
 * 
 * @author seaboat
 * @date 2017-09-20
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>An address extractor that can extract some addresses in a text.</p>
 */
public class ObjectExtractor implements IObjectExtractor {

	@Override
	public List<String> extract(String text) {
		HashMap<String, String> map = new HashMap<String, String>();
		List list = new ArrayList();
		for (String key : map.keySet()) {
			String value = map.get(key);
		}
		return list;
	}

}
