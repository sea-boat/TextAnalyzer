package com.seaboat.text.analyzer.data.structure;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Multimap;
import com.seaboat.text.analyzer.data.structure.DAGModel.Range;
import com.seaboat.text.analyzer.dict.CoreDict;

/**
 * 
 * @author seaboat
 * @date 2018-07-16
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>mdag tester.</p>
 */
public class MDAGTest {

	@Test
	public void createAndDeleteTree() {
		List<String> list = new ArrayList<String>();
		list.add("he");
		list.add("her");
		list.add("hers");
		list.add("his");
		list.add("she");
		CoreDict dict = new CoreDict(list);
		DAGModel dag = new DAGModel();
		Multimap<Integer, Range> map = dag.calc("uhershe", dict);
		for (Range r : map.values())
			System.out.println(r.from + "-" + r.to);
	}

}
