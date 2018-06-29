package com.seaboat.text.analyzer.segment;

import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-05-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>segment interface.</p>
 */
public interface Segment {
	
	public List<String> seg(String text);
	
	public List<String> Search(String text);

}
