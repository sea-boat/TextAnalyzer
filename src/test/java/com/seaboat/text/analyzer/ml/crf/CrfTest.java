package com.seaboat.text.analyzer.ml.crf;

import smile.sequence.CRF;

public class CrfTest {

	public static void main(String[] args) {

		CRF.Trainer trainer = new CRF.Trainer(10, 3);
		trainer.setLearningRate(0.3);
		trainer.setMaxNodes(100);
		trainer.setNumTrees(100);
		/* 
		 * 1,0
		 * 2,0
		 * 3,1
		 * 4,1
		 * 5,1
		 * 
		 * 1,0
		 * 3,1
		 * 4,1
		 * 5,1
		 * */
		int[][][] x = { { { 1 }, { 2 }, { 3 }, { 4 }, { 5 } }, { { 1 }, { 3 }, { 4 }, { 5 } } };
		int[][] y = { { 0, 0, 1, 1, 1 }, { 0, 1, 1, 1 } };
		CRF crf = trainer.train(x, y);
		int[][] _x = { { 3 }, { 4 }, { 5 } };
		//		int[][] _x = { { 1 }, { 2 } };
		int[] labels = crf.predict(_x);
		for (int i : labels)
			System.out.println(i);

	}
}
