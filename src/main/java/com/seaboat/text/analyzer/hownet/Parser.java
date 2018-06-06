package com.seaboat.text.analyzer.hownet;

import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>parser interface.</p>
 */
public interface Parser {

	Multimap<String, ? extends Object> parse(String file);

}
