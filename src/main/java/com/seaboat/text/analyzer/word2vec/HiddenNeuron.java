package com.seaboat.text.analyzer.word2vec;

/**
 * 
 * @author from https://github.com/NLPchina
 * @date 2018-06-11
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hidden neuron.</p>
 */
public class HiddenNeuron extends Neuron {

	public double[] syn1;

	public HiddenNeuron(int layerSize) {
		syn1 = new double[layerSize];
	}

}
