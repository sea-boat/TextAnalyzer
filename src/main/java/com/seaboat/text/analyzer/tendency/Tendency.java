package com.seaboat.text.analyzer.tendency;

/**
 * 
 * @author from https://github.com/iamxiatian/xsimilarity
 * @date 2018-06-25
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>interface for sentiment tendency.</p>
 */
public interface Tendency {

	public double getTendency(String str);

}
