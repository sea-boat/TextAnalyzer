package com.seaboat.text.analyzer.distance;

import com.seaboat.text.analyzer.word2vec.Word2Vec;

public class EditBlock {

	private String word;

	private String pos;

	private static double THRESHOLD = 0.85;

	public EditBlock(String s, String pos) {
		this.word = s;
		this.pos = pos;
	}

	public double getDeletionCost() {
		return word.length();
	}

	public double getInsertionCost() {
		return word.length();
	}

	public String getWord() {
		return word;
	}

	public String getPos() {
		return pos;
	}

	public double getSubstitutionCost(EditBlock block) {
		if (!pos.equals(block.getPos()))
			return 1.0;
		double similarity = getSimilarity(word, block.getWord());
		if (pos.equals(block.getPos()) && similarity >= THRESHOLD)
			return 0.0;
		return 1.0 - similarity;
	}

	private double getSimilarity(String word1, String word2) {
		return Word2Vec.getInstance().wordSimilarity(word1, word2);
	}

	public String toString() {
		return word + pos;
	}

}
