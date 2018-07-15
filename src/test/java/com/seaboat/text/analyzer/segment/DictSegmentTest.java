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
		DictSegment segment = DictSegment.get();
		System.out.println(segment.seg("什么是原子核a啦"));
		System.out.println(segment.seg("中国人"));
		System.out.println(segment.seg("中国人是我"));
		System.out.println(segment.seg("人工智能是什么"));
		System.out.println(segment.seg("结合成分子时"));
		System.out.println(segment.seg("北京互联网违法和不良信息举报中心"));
		String s = "习近平指出，“不忘初心，方得始终。”正确道路要坚持走下去。特别是在当前台海形势下，两岸同胞更要坚定信心，团结前行。这一讲话深刻揭示两岸关系发展的主流和大势，明确指出推动两岸关系和平发展、推进祖国和平统一进程的方向、路径、任务和目标。“四个坚定不移”更是体现了一直以来他对对台政策以及两岸关系发展的高度重视。";
		System.out.println(segment.seg(s));
	}

}
