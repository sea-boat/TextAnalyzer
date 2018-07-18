package com.seaboat.text.analyzer.ml.hmm;

/**
 * 
 * @author seaboat
 * @date 2017-09-22
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a decoder which is implements by viterbi algorithm. it can decode pos from hmm model.</p>
 */
public class ViterbiDecoder implements Decode {

	private HMMModel model;

	public ViterbiDecoder(HMMModel model) {
		this.model = model;
	}

	@Override
	public String[] decode(String[] text) {
		int len = model.statusType.length;
		double[][] value = new double[text.length][len];
		int[][] previous = new int[text.length][len];
		int position;
		if (model.observationTypeIndex.get(text[0]) != null) {
			position = model.observationTypeIndex.get(text[0]);
			for (int j = 0; j < len; j++) {
				value[0][j] = model.statusPrioriProbability[j] * model.observationProbability[position][j];
			}
		} else {
			for (int j = 0; j < len; j++) {
				value[0][j] = 1;
			}
		}

		for (int i = 1; i < text.length; i++) {
			if (model.observationTypeIndex.get(text[i]) == null) {
				for (int j = 0; j < len; j++) {
					value[i][j] = 1;
				}
				continue;
			}
			position = model.observationTypeIndex.get(text[i]);
			for (int j = 0; j < len; j++) {
				double max = value[i - 1][0] * model.statusTransitionProbability[0][j]
						* model.observationProbability[position][j];
				int index = 0;
				for (int k = 1; k < len; k++) {
					value[i][j] = value[i - 1][k] * model.statusTransitionProbability[k][j]
							* model.observationProbability[position][j];
					if (value[i][j] > max) {
						index = k;
						max = value[i][j];
					}
				}
				previous[i][j] = index;
				value[i][j] = max;
			}
		}

		double max = -1;
		int index = 0;
		for (int i = 0; i < len; i++) {
			if (max < value[text.length - 1][i]) {
				index = i;
				max = value[text.length - 1][i];
			}
		}

		for (int i = text.length - 1; i >= 0; i--) {
			text[i] = text[i].concat("/" + model.statusType[index]);
			index = previous[i][index];
		}
		return text;
	}

}
