package com.seaboat.text.analyzer.word2vec;

import java.util.Collection;
import java.util.TreeSet;

/**
 * 
 * @author from https://github.com/NLPchina
 * @date 2018-06-11
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Haffman.</p>
 */
public class Haffman {
	private int layerSize;

	public Haffman(int layerSize) {
		this.layerSize = layerSize;
	}

	private TreeSet<Neuron> set = new TreeSet<>();

	public void make(Collection<Neuron> neurons) {
		set.addAll(neurons);
		while (set.size() > 1) {
			merger();
		}
	}

	private void merger() {
		HiddenNeuron hn = new HiddenNeuron(layerSize);
		Neuron min1 = set.pollFirst();
		Neuron min2 = set.pollFirst();
		hn.category = min2.category;
		hn.freq = min1.freq + min2.freq;
		min1.parent = hn;
		min2.parent = hn;
		min1.code = 0;
		min2.code = 1;
		set.add(hn);
	}

}
