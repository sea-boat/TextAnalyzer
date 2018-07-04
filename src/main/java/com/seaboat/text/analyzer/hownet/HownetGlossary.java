package com.seaboat.text.analyzer.hownet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.seaboat.text.analyzer.similarity.HownetSememeSimilarity;

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

	private static Multimap<String, Term> glossary = null;

	private HownetSememeSimilarity sememeSimilarity = new HownetSememeSimilarity();

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
			terms = autoCombineTerms(key, null);
		}
		return terms;
	}

	public Collection<Term> getTermsWithCombining(String key) {
		return glossary.get(key);
	}

	/*
	 * calculating unknown word by segmenting and combining the word.
	 */
	public Collection<Term> autoCombineTerms(String u_word, Collection<Term> refTerms) {
		LinkedList<Term> u_list = Lists.newLinkedList();
		if (u_word == null) {
			return u_list;
		}
		for (String word : segmentUnknownWords(u_word, 3)) {
			Collection<Term> terms = getTerms(word);
			if (u_list.size() == 0) {
				u_list.addAll(terms);
				continue;
			}
			LinkedList<Term> tmp = Lists.newLinkedList();
			for (Term head : terms) {
				for (Term tail : u_list) {
					if (!isUnknownWord(refTerms)) {
						for (Term ref : refTerms) {
							Term term = autoCombineTerms(head, tail, ref);
							boolean exist = false;
							for (Term c : tmp) {
								if (c.getDefine().equals(term.getDefine())) {
									exist = true;
									break;
								}
							}
							if (!exist)
								tmp.add(term);
						}
					} else {
						Term term = autoCombineTerms(head, tail, null);
						boolean exist = false;
						for (Term c : tmp) {
							if (c.getDefine().equals(term.getDefine())) {
								exist = true;
								break;
							}
						}
						if (!exist)
							tmp.add(term);
					}
				}
			}
			u_list = tmp;
		}
		if ((u_list.size() > Constants.MAX_COMBINED_COUNT)) {
			int size = Constants.MAX_COMBINED_COUNT / 3;
			for (int i = 0; i < size; i++) {
				u_list.removeLast();
			}
		}
		return u_list;
	}

	private List<String> segmentUnknownWords(String u_word, int topN) {
		List<String> results = Lists.newLinkedList();
		int count = 0;
		while (u_word != null && !u_word.equals("")) {
			String token = u_word;
			while (token.length() > 1 && isUnknownWord(glossary.get(token))) {
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

	public Term autoCombineTerms(Term head, Term tail, Term ref) {
		if (tail == null && head != null) {
			return new Term(head.getWord(), head.getPos(), head.getDefine());
		} else if (head == null && tail != null) {
			return new Term(tail.getWord(), tail.getPos(), tail.getDefine());
		}
		if (!tail.isSubstantive()) {
			return new Term(head.getWord() + tail.getWord(), head.getPos(), head.getDefine());
		}
		if (ref == null || !ref.isSubstantive()) {
			String define = tail.getDefine();
			List<String> sememeList = getAllSememes(head, true);
			for (String sememe : sememeList) {
				if (!define.contains(sememe)) {
					define = define + "," + sememe;
				}
			}
			return new Term(head.getWord() + tail.getWord(), tail.getPos(), define);
		}

		String define = tail.getMainSememe();

		List<String> refSememes = getAllSememes(ref, false);
		List<String> headSememes = getAllSememes(head, true);
		List<String> tailSememes = getAllSememes(tail, false);

		double main_similarity = sememeSimilarity.getSimilarity(tail.getMainSememe(), ref.getMainSememe());
		if (main_similarity >= Constants.PARAM_THETA) {
			for (String tail_sememe : tailSememes) {
				double max_similarity = 0.0;
				String max_ref_sememe = null;
				for (String ref_sememe : refSememes) {
					double value = sememeSimilarity.getSimilarity(tail_sememe, ref_sememe);
					if (value > max_similarity) {
						max_similarity = value;
						max_ref_sememe = ref_sememe;
					}
				}
				if (max_similarity * main_similarity >= Constants.PARAM_XI) {
					define = define + "," + tail_sememe;
					refSememes.remove(max_ref_sememe);
				}
			}
		} else {
			define = tail.getDefine();
		}
		for (String head_sememe : headSememes) {
			double max_similarity = 0.0;
			String max_ref_sememe = "";
			for (String ref_sememe : refSememes) {
				double value = sememeSimilarity.getSimilarity(getPureSememe(head_sememe), getPureSememe(ref_sememe));
				if (value > max_similarity) {
					max_similarity = value;
					max_ref_sememe = ref_sememe;
				}
			}

			if (main_similarity * max_similarity >= Constants.PARAM_OMEGA) {
				String sememe = max_ref_sememe.replace(getPureSememe(max_ref_sememe), getPureSememe(head_sememe));
				if (!define.contains(sememe)) {
					define = define + "," + sememe;
				}
			} else if (!define.contains(head_sememe)) {
				define = define + "," + head_sememe;
			}
		}
		return new Term(head.getWord() + tail.getWord(), tail.getPos(), define);
	}

	private List<String> getAllSememes(Term t, boolean includeMainSememe) {
		List<String> results = new ArrayList<String>();
		if (t != null) {
			if (includeMainSememe) {
				results.add(t.getMainSememe());
			}
			for (String sememe : t.getSecondSememes()) {
				results.add(sememe);
			}
			for (String sememe : t.getSymbolSememes()) {
				results.add(sememe);
			}
			for (String sememe : t.getRelationSememes()) {
				results.add(sememe);
			}
		}
		return results;
	}

	private String getPureSememe(String sememe) {
		String line = sememe.trim();
		if ((line.charAt(0) == '(') && (line.charAt(line.length() - 1) == ')')) {
			line = line.substring(1, line.length() - 1);
		}
		String symbol = line.substring(0, 1);
		for (int i = 0; i < Constants.Symbol_Descriptions.length; i++) {
			if (symbol.equals(Constants.Symbol_Descriptions[i][0])) {
				return line.substring(1);
			}
		}
		int pos = line.indexOf('=');
		if (pos > 0) {
			line = line.substring(pos + 1);
		}
		return line;
	}

	private boolean isUnknownWord(Collection<Term> terms) {
		return terms == null || terms.size() == 0;
	}

}
