package com.seaboat.text.analyzer.extractor;

import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-06-14
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Information extractor interface.</p>
 */
public interface InfoExtractor {

	public List getIDs(String text);

	public List getNames(String text);

	public List getAddrs(String text);

}
