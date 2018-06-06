package com.seaboat.text.analyzer.hownet;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>sememe type.</p>
 */
public interface SememeType {

	public static final int Event = 1;

	public static final int Entity = 2;

	public static final int Attribute = 3;

	public static final int Quantity = 4;

	public static final int AValue = 5;

	public static final int QValue = 6;

	public static final int SecondaryFeature = 7;

	public static final int Syntax = 8;

	public static final int EventRoleAndFeature = 9;

	public static final int Unknown = 0;

}
