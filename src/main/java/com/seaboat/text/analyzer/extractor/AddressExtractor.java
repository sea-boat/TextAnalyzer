package com.seaboat.text.analyzer.extractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.seaboat.text.analyzer.IAddressExtractor;

/**
 * 
 * @author seaboat
 * @date 2017-09-19
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>An address extractor that can extract some addresses in a text.</p>
 */
public class AddressExtractor implements IAddressExtractor {
	static Properties prop = new Properties();

	@Override
	public List<String> extract(String text) {
		HashMap<String, String> map = new HashMap<String, String>();
		List<String> list = new ArrayList<String>();
		for (String key : map.keySet()) {
			String value = map.get(key);
			if (value.equals("地名"))
				list.add(key);
		}
		return list;
	}

}
