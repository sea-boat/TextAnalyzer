package com.seaboat.text.analyzer.segment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.dict.CoreDict;

/**
 * 
 * @author seaboat
 * @date 2018-05-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word segment based dictionary.</p>
 */
public class DictSegment implements Segment {

	public enum MODE {
		NORMAL, INDEX
	}

	private static Logger logger = Logger.getLogger(DictSegment.class);
	private static DictSegment instance = null;
	private static CoreDict dict = CoreDict.get();
	private static MODE mode = MODE.INDEX;

	public static DictSegment get() {
		if (instance != null)
			return instance;
		synchronized (DictSegment.class) {
			if (instance == null)
				instance = new DictSegment();
		}
		return instance;
	}

	private DictSegment() {
	}

	@Override
	public List<String> seg(String text) {
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = new ArrayList<String>();
		if (mode == MODE.INDEX) {
			String s = text;
			int index = 0;
			while (true) {
				List<Integer> temp_list = dict.prefixSearch(s.substring(index, s.length()));
				if (temp_list.size() == 0) {
					words.add(s.substring(index, index + 1) + "/un");
					index++;
					if (index == s.length())
						break;
					continue;
				} else {
					int len = dict.getStringByIndex(temp_list.get(0)).length();
					for (int i : temp_list) {
						if (dict.getStringByIndex(i).length() > len)
							len = dict.getStringByIndex(i).length();
						words.add(dict.getStringByIndex(i) + "/" + dict.getPostType(i));
					}
					index = index + len;
				}
				if (index >= s.length())
					break;
			}
		}
		logger.debug("segment elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
		return words;
	}

	@Override
	public List<String> Search(String text) {
		//		return tree.acSearch(text);
		return null;
	}

}
