package com.seaboat.text.analyzer.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.dict.CoreWordDict;

/**
 * 
 * @author seaboat
 * @date 2018-08-16
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word segment based dictionary which is using rule-based.</p>
 * <p>"forward maximum matching"</p>
 * <p>"reverse maximum matching"</p>
 */
public class RuleBasedSegment implements Segment {

	enum TYPE {
		//forward maximum matching
		FMM,
		//reverse maximum matching
		RMM
	};

	private static Logger logger = Logger.getLogger(RuleBasedSegment.class);
	private static RuleBasedSegment instance = null;
	private static CoreWordDict dict = CoreWordDict.get();
	private static TYPE type = TYPE.FMM;

	public static RuleBasedSegment get() {
		if (instance != null)
			return instance;
		synchronized (RuleBasedSegment.class) {
			if (instance == null)
				instance = new RuleBasedSegment();
		}
		return instance;
	}

	public static void setType(TYPE type) {
		RuleBasedSegment.type = type;
	}

	private RuleBasedSegment() {
	}

	@Override
	public List<String> seg(String text) {
		switch (type) {
		case FMM:
			return fmm(text);
		case RMM:
			return rmm(text);
		}
		return null;
	}

	private List<String> rmm(String text) {
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = new ArrayList<String>();
		int LEN = 10;
		while (text.length() > 0) {
			String temp;
			if (text.length() < LEN) {
				temp = text;
			} else {
				temp = text.substring(text.length() - LEN);
			}

			while (temp.length() > 0) {
				int id = dict.exactlySearch(temp);
				if (id != -1 || temp.length() == 1) {
					if (temp.length() == 1 && id == -1)
						words.add(temp + "/un");
					else
						words.add(temp + "/" + dict.getPostType(id));
					text = text.substring(0, text.length() - temp.length());
					break;
				} else {
					temp = temp.substring(1);
				}
			}
		}
		Collections.reverse(words);
		logger.debug("segment elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
		return words;
	}

	private List<String> fmm(String text) {
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = new ArrayList<String>();
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
				//last is the longest
				String longest = dict.getStringByIndex(temp_list.get(temp_list.size() - 1));
				int len = longest.length();
				words.add(longest + "/" + dict.getPostType(temp_list.get(temp_list.size() - 1)));
				index = index + len;
			}
			if (index >= s.length())
				break;
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
