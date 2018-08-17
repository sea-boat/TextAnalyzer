package com.seaboat.text.analyzer.segment;

import org.junit.Test;

import com.seaboat.text.analyzer.segment.RuleBasedSegment.TYPE;

/**
 * 
 * @author seaboat
 * @date 2018-08-16
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>rule-based segment tester.</p>
 */
public class RuleBasedSegmentTest {

	@Test
	public void test() {
		RuleBasedSegment segment = RuleBasedSegment.get();
		System.out.println(segment.seg("什么是原子核a啦"));
		System.out.println(segment.seg("中国人"));
		System.out.println(segment.seg("中国人是我"));
		System.out.println(segment.seg("人工智能是什么"));
		System.out.println(segment.seg("结合成分子时"));

		RuleBasedSegment.setType(TYPE.RMM);
		System.out.println(segment.seg("什么是原子核a啦"));
		System.out.println(segment.seg("中国人"));
		System.out.println(segment.seg("中国人是我"));
		System.out.println(segment.seg("人工智能是什么"));
		System.out.println(segment.seg("结合成分子时"));
	}

}
