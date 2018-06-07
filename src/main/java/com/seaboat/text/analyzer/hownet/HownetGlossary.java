package com.seaboat.text.analyzer.hownet;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the glossary hownet.</p>
 */
public class HownetGlossary {

	private static Logger logger = Logger.getLogger(HownetGlossary.class);

	private static Multimap<String, Term> glossary = null;

	private static HownetGlossary instance;

	private static String FILE = "/hownet-glossary.xml";

	private HownetGlossary() {
		glossary = new GlossaryParser().parse(FILE);
	}

	public static HownetGlossary getInstance() {
		if (instance == null) {
			synchronized (HownetGlossary.class) {
				if (instance == null)
					instance = new HownetGlossary();
			}
		}
		return instance;
	}

	public Collection<Term> getTerms(String key) {
		Collection<Term> terms = glossary.get(key);
		if (isUnknownWord(terms)) {
//			terms = autoCombineTerms(key, null);
		}
		return terms;
	}

	/*
	 * calculating unknown word by segmenting and combining the word.
	 */
//	public Collection<Term> autoCombineTerms(String u_word, Collection<Term> refTerms) {
//		List<Term> u_list = Lists.newLinkedList();
//		if (u_word == null) {
//			return u_list;
//		}
//		for (String word : segmentUnknownWords(u_word, 3)) {
//			Collection<Term> terms = getTerms(word);
//			if (u_list.size() == 0) {
//				u_list.addAll(terms);
//				continue;
//			}
//
//			List<Term> tmp = Lists.newLinkedList();
//			for (Term head : terms) {
//				for (Term tail : u_list) {
//					if (!isUnknownWord(refTerms)) {
//						for (Term ref : refTerms) {
//							tmp.addByDefine(autoCombineTerms(head, tail, ref));
//						}
//					} else {
//						tmp.addByDefine(autoCombineTerms(head, tail, null));
//					}
//				}
//			}
//			u_list = tmp;
//		}
//
//		if ((u_list.size() > MAX_COMBINED_COUNT)) {
//			u_list.removeLast(MAX_COMBINED_COUNT / 3);
//		}
//
//		return u_list;
//	}

	private List<String> segmentUnknownWords(String u_word, int topN) {
		List<String> results = new LinkedList<String>();
		int count = 0;
		while (u_word != null && !u_word.equals("")) {
			String token = u_word;
			while (token.length() > 1 && isUnknownWord(getTerms(token))) {
				token = token.substring(1);
			}
			results.add(token);
			count++;
			if (count >= topN)
				break;

			u_word = u_word.substring(0, (u_word.length() - token.length()));
		}

		return results;
	}

//	public Term autoCombineTerms(Term head, Term tail, Term ref) {
//		if (tail == null && head != null) {
//			return new Term(head.getWord(), head.getPos(), head.getDefine());
//		} else if (head == null && tail != null) {
//			return new Term(tail.getWord(), tail.getPos(), tail.getDefine());
//		}
//		if (!tail.isbSubstantive()) {
//			return new Term(head.getWord() + tail.getWord(), head.getPos(), head.getDefine());
//		}
//		if (ref == null || !ref.isbSubstantive()) {
//			String define = tail.getDefine();
//			List<String> sememeList = getAllSememes(head, true);
//			for (String sememe : sememeList) {
//				if (!define.contains(sememe)) {
//					define = define + "," + sememe;
//				}
//			}
//			return new Term(head.getWord() + tail.getWord(), tail.getPos(), define);
//		}
//
//		String define = tail.getMainSememe();
//
//		List<String> refSememes = getAllSememes(ref, false);
//		List<String> headSememes = getAllSememes(head, true);
//		List<String> tailSememes = getAllSememes(tail, false);
//
//		double main_similarity = sememeParser.getSimilarity(tail.getMainSememe(), ref.getMainSememe());
//		if (main_similarity >= PARAM_THETA) {
//			// 求交集
//			for (String tail_sememe : tailSememes) {
//				double max_similarity = 0.0;
//				String max_ref_sememe = null;
//				for (String ref_sememe : refSememes) {
//					double value = sememeParser.getSimilarity(tail_sememe, ref_sememe);
//					if (value > max_similarity) {
//						max_similarity = value;
//						max_ref_sememe = ref_sememe;
//					}
//				}
//
//				// 如果tail_sememe与参照概念中的相似度最大的义原经theta约束后超过阈值XI，则加入生成的组合概念定义中
//				if (max_similarity * main_similarity >= PARAM_XI) {
//					define = define + "," + tail_sememe;
//					refSememes.remove(max_ref_sememe);
//				}
//			} // end for
//		} else {
//			define = tail.getDefine();
//		} // end if
//
//		// 合并第一个概念的义原到组合概念定义中
//		for (String head_sememe : headSememes) {
//			double max_similarity = 0.0;
//			String max_ref_sememe = "";
//			for (String ref_sememe : refSememes) {
//				double value = sememeParser.getSimilarity(getPureSememe(head_sememe), getPureSememe(ref_sememe));
//				if (value > max_similarity) {
//					max_similarity = value;
//					max_ref_sememe = ref_sememe;
//				}
//			}
//
//			if (main_similarity * max_similarity >= PARAM_OMEGA) {
//				// 调整符号关系, 用参照概念的符号关系替换原符号关系, 通过把参照概念的非符号部分替换成前面义原的非符号内容即可
//				String sememe = max_ref_sememe.replace(getPureSememe(max_ref_sememe), getPureSememe(head_sememe));
//				if (!define.contains(sememe)) {
//					define = define + "," + sememe;
//				}
//			} else if (!define.contains(head_sememe)) {
//				define = define + "," + head_sememe;
//			}
//		} // end for
//
//		return new Term(head.getWord() + tail.getWord(), tail.getPos(), define);
//	}

	private boolean isUnknownWord(Collection<Term> terms) {
		return terms == null || terms.size() == 0;
	}

}
