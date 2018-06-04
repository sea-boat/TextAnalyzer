package com.seaboat.text.analyzer.segment;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-05-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>address extractor tester.</p>
 */
public class DictSegmentTest {

	@Test
	public void test() {
		DictSegment segment = new DictSegment();
		System.out.println(segment.seg("我是中国人"));
		System.out.println(segment.seg("人工智能是什么"));
		System.out.println(segment.seg("结合成分子时"));
		System.out.println(segment.seg("北京互联网违法和不良信息举报中心"));
	}
	
}
