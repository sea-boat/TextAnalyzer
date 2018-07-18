package com.seaboat.text.analyzer.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2018-07-18
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>operations for corpus.</p>
 */
public class CorpusUtil {
	protected static Logger logger = Logger.getLogger(CorpusUtil.class);

	/**
	 * @return word list, pos list
	 */
	public static List<String[]>[] readPeopleDailyCorpus(String fileName) {
		BufferedReader reader = null;
		List<String[]> contents = new ArrayList<String[]>();
		List<String[]> characters = new ArrayList<String[]>();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.replaceAll("\\[", "");
				line = line.replaceAll("\\]/nz", "");
				line = line.replaceAll("\\]/mq", "");
				line = line.replaceAll("\\]/nt", "");
				String[] terms = line.split("(/[a-z]*\\s{0,})");
				terms = line.split("\\s{1,}[^a-z]*");
				terms[0] = terms[0].substring(terms[0].indexOf("/") + 1);
				contents.add(line.split("(/[a-z]*\\s{0,})"));
				characters.add(terms);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("close file error");
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<String[]>[] lists = new ArrayList[2];
		lists[0] = contents;
		lists[1] = characters;
		return lists;
	}

}
