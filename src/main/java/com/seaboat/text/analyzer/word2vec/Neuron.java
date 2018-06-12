package com.seaboat.text.analyzer.word2vec;

/**
 * 
 * @author from https://github.com/NLPchina
 * @date 2018-06-11
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>neuron.</p>
 */
public abstract class Neuron implements Comparable<Neuron> {
	public double freq;
	public Neuron parent;
	public int code;
	public int category = -1;

	@Override
	public int compareTo(Neuron neuron) {
		if (this.category == neuron.category) {
			if (this.freq > neuron.freq) {
				return 1;
			} else {
				return -1;
			}
		} else if (this.category > neuron.category) {
			return 1;
		} else {
			return -1;
		}
	}
}
