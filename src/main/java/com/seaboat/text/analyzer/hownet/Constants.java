package com.seaboat.text.analyzer.hownet;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>constants about hownet.</p>
 */
public class Constants {
	public static final String Symbol_Descriptions[][] = { { "#", "表示与其相关" }, { "%", "是其部分" },
			{ "$", "可以被该V处置，或是该V的受事、对象、领有物，或内容" }, { "*", "施事或工具" }, { "+", "所标记的角色是隐性的，几乎在实际语言中不会出现" }, { "&", "指向" },
			{ "~", "多半是，多半有，很可能" }, { "@", "可以做V的空间或时间" }, { "?", "可以使N的材料" }, { "(", "至于其中的应该是一个词标记" },
			{ "^", "不存在，或没有，或不能" }, { "!", "表示某一属性为一敏感的属性，如味道之与食物" }, { "[", "标示概念的共性属性" } };
	public static final double delta = 0.2;
	public static final double beta1 = 0.5;
	public static final double beta2 = 0.2;
	public static final double beta3 = 0.17;
	public static final double beta4 = 0.13;
}
