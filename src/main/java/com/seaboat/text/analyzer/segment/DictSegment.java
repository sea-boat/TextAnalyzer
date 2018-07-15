package com.seaboat.text.analyzer.segment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.data.structure.DoubleArrayTrie;

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
	private static String DIC_FILE = "/core.dic";
	private static String[] dictionary = null;
	private static int[] frequencies = null;
	private static byte[] pos = null;
	private static String[] POS_TYPE = null;
	private static DoubleArrayTrie tree = null;
	private static DictSegment instance = null;
	private static List<String> words = null;
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
		tree = new DoubleArrayTrie();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = DictSegment.loadDict();
		tree.build(words);
		logger.debug("loading dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
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
				List<Integer> temp_list = tree.commonPrefixSearch(s.substring(index, s.length()));
				if (temp_list.size() == 0) {
					words.add(s.substring(index, index + 1) + "/un");
					index++;
					if (index == s.length())
						break;
					continue;
				} else {
					int len = dictionary[temp_list.get(0)].length();
					for (int i : temp_list) {
						if (dictionary[i].length() > len)
							len = dictionary[i].length();
						words.add(dictionary[i] + "/" + POS_TYPE[pos[i]]);
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

	private static List<String> loadDict() {
		InputStreamReader read;
		try {
			read = new InputStreamReader(DictSegment.class.getResourceAsStream(DIC_FILE), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line;
			line = bufferedReader.readLine();
			words = new ArrayList<String>(Integer.parseInt(line));
			Set<String> set = new HashSet<String>();
			Map<String, String> mapN = new HashMap<String, String>();
			Map<String, String> mapF = new HashMap<String, String>();
			while ((line = bufferedReader.readLine()) != null) {
				String[] ss = line.split("	");
				if (ss.length == 3) {
					words.add(ss[0]);
					set.add(ss[1]);
					mapN.put(ss[0], ss[1]);
					mapF.put(ss[0], ss[2]);
				}
			}
			Collections.sort(words);
			POS_TYPE = set.toArray(new String[set.size()]);
			dictionary = new String[words.size()];
			for (int i = 0; i < dictionary.length; i++)
				dictionary[i] = words.get(i);
			frequencies = new int[words.size()];
			for (int i = 0; i < frequencies.length; i++)
				frequencies[i] = Integer.parseInt(mapF.get(words.get(i)));
			pos = new byte[words.size()];
			for (int i = 0; i < pos.length; i++) {
				pos[i] = getPosIndex(mapN.get(words.get(i)));
			}
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return words;
	}

	private static byte getPosIndex(String pos) {
		for (int i = 0; i < POS_TYPE.length; i++) {
			if (POS_TYPE[i].equalsIgnoreCase(pos))
				return (byte) i;
		}
		return -1;
	}

	@Override
	public List<String> Search(String text) {
		//		return tree.acSearch(text);
		return null;
	}

}
