package com.seaboat.text.analyzer.tagging;

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
    double[][] value = new double[text.length][model.posNum];
    int[][] previous = new int[text.length][model.posNum];
    int position;
    if (model.phrasePosition.get(text[0]) != null) {
      position = model.phrasePosition.get(text[0]);
      for (int j = 0; j < model.posNum; j++) {
        value[0][j] = model.prioriProbability[j] * model.phraseInPOSProbability[position][j];
      }
    } else {
      for (int j = 0; j < model.posNum; j++) {
        value[0][j] = 1;
      }
    }

    for (int i = 1; i < text.length; i++) {
      if (model.phrasePosition.get(text[i]) == null) {
        for (int j = 0; j < model.posNum; j++) {
          value[i][j] = 1;
        }
        continue;
      }
      position = model.phrasePosition.get(text[i]);
      for (int j = 0; j < model.posNum; j++) {
        double max =
            value[i - 1][0] * model.posTransformProbability[0][j]
                * model.phraseInPOSProbability[position][j];
        int index = 0;
        for (int k = 1; k < model.posNum; k++) {
          value[i][j] =
              value[i - 1][k] * model.posTransformProbability[k][j]
                  * model.phraseInPOSProbability[position][j];
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
    for (int i = 0; i < model.posNum; i++) {
      if (max < value[text.length - 1][i]) {
        index = i;
        max = value[text.length - 1][i];
      }
    }

    for (int i = text.length - 1; i >= 0; i--) {
      text[i] = text[i].concat("/" + model.diffPOS[index]);
      index = previous[i][index];
    }
    return text;
  }

}
