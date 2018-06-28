package com.seaboat.text.analyzer.segment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

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

	private static Logger logger = Logger.getLogger(DictSegment.class);
	private static String DIC_FILE = "/finger.dic";
	private static ACTrieTree tree = new ACTrieTree();

	@Override
	public List<String> seg(String text) {
		int flag = 0;
		int delta = 1;
		List<String> words = new ArrayList<String>();
		while (flag + delta <= text.length()) {
			String temp = text.substring(flag, flag + delta);
			if (tree.get(temp) != null) {
				if ((flag + delta) == text.length()) {
					words.add(temp);
					break;
				}
				delta++;
				continue;
			}
			words.add(temp.substring(0, temp.length() - 1));
			flag = flag + delta - 1;
			delta = 1;
		}
		return words;
	}

	static {
		List<String> words = DictSegment.loadDict();
		for (String s : words)
			tree.put(s);
	}

	private static List<String> loadDict() {
		InputStreamReader read;
		List<String> words = new LinkedList<String>();
		try {
			read = new InputStreamReader(DictSegment.class.getResourceAsStream(DIC_FILE), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] ss = line.split("	");
				words.add(ss[0]);
			}
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return words;
	}

}
