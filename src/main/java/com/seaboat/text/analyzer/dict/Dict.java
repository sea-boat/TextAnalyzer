package com.seaboat.text.analyzer.dict;

import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-07-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>an interface for dict.</p>
 */
public interface Dict {

	List<String> loadDict(String path);

	public List<Integer> prefixSearch(String text);

	public String getStringByIndex(Integer i);

}
