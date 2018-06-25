package com.seaboat.text.analyzer.tendency;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.seaboat.text.analyzer.hownet.HownetGlossary;
import com.seaboat.text.analyzer.hownet.HownetSememe;
import com.seaboat.text.analyzer.hownet.Term;
import com.seaboat.text.analyzer.similarity.HownetSememeSimilarity;

/**
 * 
 * @author from https://github.com/iamxiatian/xsimilarity
 * @date 2018-06-25
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Words' sentiment tendency which is based on Hownet.</p>
 */
public class WordSentimentTendency implements Tendency {
	public static String[] POSITIVE_SEMEMES = new String[] { "良", "喜悦", "夸奖", "满意", "期望", "注意", "致敬", "喜欢", "专", "敬佩",
			"同意", "爱惜", "愿意", "思念", "拥护", "祝贺", "福", "需求", "奖励", "致谢", "欢迎", "羡慕", "感激", "爱恋" };

	public static String[] NEGATIVE_SEMEMES = new String[] { "莠", "谴责", "害怕", "生气", "悲哀", "着急", "轻视", "羞愧", "烦恼", "灰心",
			"犹豫", "为难", "懊悔", "厌恶", "怀疑", "怜悯", "忧愁", "示怒", "不满", "仇恨", "埋怨", "失望", "坏" };

	HownetSememeSimilarity similarity = new HownetSememeSimilarity();

	@Override
	public double getTendency(String str) {
		double positive = getSentiment(str, POSITIVE_SEMEMES);
		double negative = getSentiment(str, NEGATIVE_SEMEMES);
		return positive - negative;
	}

	public double getSentiment(String word, String[] candidateSememes) {
		Collection<Term> terms = HownetGlossary.getInstance().getTerms(word);
		Set<String> sememes = new HashSet<String>();
		for (Term c : terms) {
			sememes.addAll(c.getAllSememeNames());
		}

		double max = 0.0;
		for (String item : sememes) {
			double total = 0.0;
			for (String positiveSememe : candidateSememes) {
				double value = similarity.getSimilarity(item, positiveSememe);
				if (value > 0.9) {
					return value;
				}
				total += value;
			}
			double sim = total / candidateSememes.length;
			if (sim > max) {
				max = sim;
			}
		}
		return max;
	}

}
