package com.seaboat.text.analyzer.dict;

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
import com.seaboat.text.analyzer.segment.DictSegment;

/**
 * 
 * @author seaboat
 * @date 2018-07-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>core dictionary.</p>
 */
public class CoreDict implements Dict {

	private static Logger logger = Logger.getLogger(CoreDict.class);

	private static DoubleArrayTrie tree = null;
	private static String DIC_FILE = "/core.dic";
	public static String[] dictionary = null;
	private static int[] frequencies = null;
	private static byte[] pos = null;
	private static String[] POS_TYPE = null;
	private static CoreDict instance;

	private CoreDict() {
		tree = new DoubleArrayTrie();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = loadDict(DIC_FILE);
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	public CoreDict(List<String> words) {
		tree = new DoubleArrayTrie();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		initDict(words);
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	private void initDict(List<String> words) {
		Collections.sort(words);
		dictionary = new String[words.size()];
		for (int i = 0; i < dictionary.length; i++)
			dictionary[i] = words.get(i);
	}

	public static CoreDict get() {
		if (instance != null)
			return instance;
		synchronized (CoreDict.class) {
			if (instance == null)
				instance = new CoreDict();
			return instance;
		}
	}

	public List<String> loadDict(String path) {
		List<String> words = null;
		InputStreamReader read;
		try {
			read = new InputStreamReader(DictSegment.class.getResourceAsStream(path), "UTF-8");
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

	private byte getPosIndex(String pos) {
		for (int i = 0; i < POS_TYPE.length; i++) {
			if (POS_TYPE[i].equalsIgnoreCase(pos))
				return (byte) i;
		}
		return -1;
	}

	public List<Integer> prefixSearch(String text) {
		return tree.commonPrefixSearch(text);
	}

	public String getStringByIndex(Integer i) {
		return dictionary[i];
	}

	public String getPostType(int i) {
		return POS_TYPE[pos[i]];
	}
}
