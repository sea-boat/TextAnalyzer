package com.seaboat.text.analyzer.data.structure;

import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.seaboat.text.analyzer.dict.Dict;

/**
 * 
 * @author seaboat
 * @date 2018-07-16
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Using a map to store DAG status.</p>
 */
public class DAGModel {

	public Multimap<Integer, Range> calc(String s, Dict dict) {
		Multimap<Integer, Range> dag = ArrayListMultimap.create();
		int index = 0;
		int id = 1;
		String temp;
		while (true) {
			temp = s.substring(index, s.length());
			List<Integer> temp_list = dict.prefixSearch(temp);
			if (temp_list.size() == 0) {
				if (index == s.length())
					break;
				index++;
				continue;
			} else {
				for (int i : temp_list) {
					int len = dict.getStringByIndex(i).length();
					dag.put(id++, new Range(index, index + len - 1));
				}
				index++;
			}
			if (index >= s.length())
				break;
		}
		return dag;
	}

	class Range {
		int from;
		int to;

		public Range(int from, int to) {
			this.from = from;
			this.to = to;
		}

	}
}
