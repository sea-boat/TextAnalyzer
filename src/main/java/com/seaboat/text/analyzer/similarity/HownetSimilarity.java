package com.seaboat.text.analyzer.similarity;

import java.util.Collection;

import com.seaboat.text.analyzer.hownet.Constants;
import com.seaboat.text.analyzer.hownet.HownetGlossary;
import com.seaboat.text.analyzer.hownet.Term;

/**
 * 
 * @author seaboat
 * @date 2018-06-07
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>calculate similarity of hownet.</p>
 */
public class HownetSimilarity implements ISimilarity {

	HownetSememeSimilarity sememeSimilarity = new HownetSememeSimilarity();

	public enum SET_OPERATE_TYPE {
		AVERAGE, FUZZY
	};

	private SET_OPERATE_TYPE currentSetOperateType = SET_OPERATE_TYPE.AVERAGE;

	@Override
	public double getSimilarity(String s1, String s2) {
		double similarity = 0.0;
		if (s1.equals(s2)) {
			return 1.0;
		}
		Collection<Term> termColl1 = HownetGlossary.getInstance().getTerms(s1);
		Collection<Term> termColl2 = HownetGlossary.getInstance().getTerms(s2);
		if (isBlank(termColl1) || isBlank(termColl2)) {
			return 0.0;
		}
		for (Term t1 : termColl1) {
			for (Term t2 : termColl2) {
				double v = getSimilarity(t1, t2);
				if (v > similarity) {
					similarity = v;
				}
				if (similarity == 1.0) {
					break;
				}
			}
		}
		return similarity;
	}

	public double getSimilarity(Term t1, Term t2) {
		double similarity = 0.0;
		if (t1 == null || t2 == null || !t1.getPos().equals(t2.getPos())) {
			return 0.0;
		}
		if (t1.equals(t2)) {
			return 1.0;
		}
		if (t1.isSubstantive() != t2.isSubstantive()) {
			return 0.0;
		} else if (t1.isSubstantive() == false) {
			similarity = sememeSimilarity.getSimilarity(t1.getMainSememe(), t2.getMainSememe());
		} else {
			double sim1 = sememeSimilarity.getSimilarity(t1.getMainSememe(), t2.getMainSememe());
			double sim2 = getSimilarity(t1.getSecondSememes(), t2.getSecondSememes());
			double sim3 = getSimilarity(t1.getRelationSememes(), t2.getRelationSememes());
			double sim4 = getSimilarity(t1.getSymbolSememes(), t2.getSymbolSememes());
			similarity = calculate(sim1, sim2, sim3, sim4);
		}
		return similarity;
	}

	protected double calculate(double sim_v1, double sim_v2, double sim_v3, double sim_v4) {
		return Constants.beta1 * sim_v1 + Constants.beta2 * sim_v1 * sim_v2 + Constants.beta3 * sim_v1 * sim_v2 * sim_v3
				+ Constants.beta4 * sim_v1 * sim_v2 * sim_v3 * sim_v4;
	}

	protected double getSimilarity(String[] sememes1, String[] sememes2) {
		if (currentSetOperateType == SET_OPERATE_TYPE.FUZZY) {
			return getSimilarity_Fuzzy(sememes1, sememes2);
		} else {
			return getSimilarity_AVG(sememes1, sememes2);
		}
	}

	private double getSimilarity_AVG(String[] sememes1, String[] sememes2) {
		double similarity = 0.0;
		double scoreArray[][];
		if (isBlank(sememes1) || isBlank(sememes2)) {
			if (isBlank(sememes1) && isBlank(sememes2)) {
				return 1.0;
			} else {
				return Constants.delta;
			}
		}
		double score = 0.0;
		int arrayLen = sememes1.length > sememes2.length ? sememes1.length : sememes2.length;
		scoreArray = new double[arrayLen][arrayLen];
		for (int i = 0; i < arrayLen; i++) {
			for (int j = 0; j < arrayLen; j++) {
				scoreArray[i][j] = 0;
			}
		}
		for (int i = 0; i < sememes1.length; i++) {
			for (int j = 0; j < sememes2.length; j++) {
				scoreArray[i][j] = sememeSimilarity.getSimilarity(sememes1[i], sememes2[j]);
			}
		}
		score = 0.0;
		while (scoreArray.length > 0) {
			double[][] tmp;
			int row = 0;
			int column = 0;
			double max = scoreArray[row][column];
			for (int i = 0; i < scoreArray.length; i++) {
				for (int j = 0; j < scoreArray[i].length; j++) {
					if (scoreArray[i][j] > max) {
						row = i;
						column = j;
						max = scoreArray[i][j];
					}
				}
			}
			score += max;
			tmp = new double[scoreArray.length - 1][scoreArray.length - 1];
			for (int i = 0; i < scoreArray.length; i++) {
				if (i == row) {
					continue;
				}
				for (int j = 0; j < scoreArray[i].length; j++) {
					if (j == column) {
						continue;
					}
					int tmprow = i;
					int tmpcol = j;
					if (i > row)
						tmprow--;
					if (j > column)
						tmpcol--;
					tmp[tmprow][tmpcol] = scoreArray[i][j];
				}
			}
			scoreArray = tmp;
		}
		similarity = score / arrayLen;
		return similarity;
	}

	protected double getSimilarity_Fuzzy(String[] sememes1, String[] sememes2) {
		// @ TODO
		return 0.0;
	}

	private boolean isBlank(Collection<Term> term) {
		return term.size() == 0 || term == null;
	}

	private boolean isBlank(String[] str) {
		return str == null || str.length == 0;
	}

}
