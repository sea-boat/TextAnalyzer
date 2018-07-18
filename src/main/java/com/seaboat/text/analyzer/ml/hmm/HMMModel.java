package com.seaboat.text.analyzer.ml.hmm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.exception.SizeException;

/**
 * 
 * @author seaboat
 * @date 2017-09-22
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a hmm model which is for part of speech. it provides training method.</p>
 */
public class HMMModel implements Serializable {
	protected static Logger logger = Logger.getLogger(HMMModel.class);
	private static final long serialVersionUID = -8307826997830463783L;
	String[] statusType;
	String[] observationType;
	double[] statusPrioriProbability;
	double[][] statusTransitionProbability;
	double[][] observationProbability;
	Hashtable<String, Integer> observationTypeIndex = new Hashtable<String, Integer>();

	/*
	 * 观察列表和状态列表，比如“我/是/中国/人”和“代词/动词/名词/名词”
	 */
	public void train(List<String[]> observationList, List<String[]> statusList) throws SizeException {
		checkSize(observationList, statusList);
		String[] status = getAllStatus(statusList);
		Hashtable<String, Integer> statusFreq = getStatusFreq(status);
		calcStatusType(statusFreq);
		String[] observations = getAllObservation(observationList);
		this.observationType = calcObservationFreq(observations);
		Hashtable<String, Integer> transitionFreq = calcStatusTransitionFreq(status);
		this.statusPrioriProbability = calcStatusPrioriProbability(statusFreq);
		this.statusTransitionProbability = calcStatusTransitionProbability(statusFreq, transitionFreq);
		Hashtable<String, Integer> blockFreq = calcBlockFreq(observations, status);
		this.observationProbability = calcObservationProbability(blockFreq);
	}

	private void checkSize(List<String[]> observationList, List<String[]> statusList) throws SizeException {
		if (observationList.size() != statusList.size())
			throw new SizeException("observation list size must be the same with status list size.");
		for (int i = 0; i < observationList.size(); i++) {
			if (observationList.get(i).length != statusList.get(i).length)
				throw new SizeException("observation list size must be the same with status list size.");
		}
	}

	private Hashtable<String, Integer> getStatusFreq(String[] status) {
		Hashtable<String, Integer> statusFreq = new Hashtable<String, Integer>();
		for (int i = 1; i < status.length; i++) {
			if (statusFreq.containsKey(status[i])) {
				statusFreq.put(status[i], statusFreq.get(status[i]) + 1);
			} else {
				statusFreq.put(status[i], 1);
			}
		}
		return statusFreq;
	}

	private double[][] calcObservationProbability(Hashtable<String, Integer> blockFreq) {
		double[][] observationProbability = new double[observationType.length][statusType.length];
		for (int i = 0; i < observationType.length; i++) {
			String o = observationType[i];
			int total = 0;
			for (int j = 0; j < statusType.length; j++) {
				String chars = statusType[j];
				if (blockFreq.containsKey(o + "/" + chars)) {
					int num = blockFreq.get(o + "/" + chars);
					total += num;
				}
			}
			for (int j = 0; j < statusType.length; j++) {
				String chars = statusType[j];
				if (blockFreq.containsKey(o + "/" + chars)) {
					int numerator = blockFreq.get(o + "/" + chars);
					observationProbability[i][j] = (double) numerator / total;
				} else {
					observationProbability[i][j] = 0.00001;// avoid zero multiply
				}
			}
		}
		return observationProbability;
	}

	private Hashtable<String, Integer> calcBlockFreq(String[] observations, String[] status) {
		Hashtable<String, Integer> blockFreq = new Hashtable<String, Integer>();
		for (int i = 0; i < observations.length; i++) {
			String s = observations[i] + "/" + status[i];
			if (blockFreq.containsKey(s)) {
				blockFreq.put(s, blockFreq.get(s) + 1);
			} else {
				blockFreq.put(s, 1);
			}
		}
		return blockFreq;
	}

	private double[][] calcStatusTransitionProbability(Hashtable<String, Integer> statusFreq,
			Hashtable<String, Integer> transitionFreq) {
		double[][] transitionProbability = new double[statusType.length][statusType.length];
		for (int i = 0; i < statusType.length; i++) {
			for (int j = 0; j < statusType.length; j++) {
				String front = statusType[i];
				String last = statusType[j];
				if (transitionFreq.containsKey(front + "," + last)) {
					int numerator = transitionFreq.get(front + "," + last);
					int denominator = statusFreq.get(front);
					transitionProbability[i][j] = (double) numerator / denominator;
				}
			}
		}
		return transitionProbability;
	}

	private double[] calcStatusPrioriProbability(Hashtable<String, Integer> statusFreq) {
		double[] p = new double[statusType.length];
		int allPOSCount = 0;
		for (int i = 0; i < statusType.length; i++) {
			allPOSCount += statusFreq.get(statusType[i]);
		}
		for (int i = 0; i < statusType.length; i++) {
			p[i] = statusFreq.get(statusType[i]) * 1.0 / allPOSCount;
		}
		return p;
	}

	private Hashtable<String, Integer> calcStatusTransitionFreq(String[] status) {
		Hashtable<String, Integer> transitionFreq = new Hashtable<String, Integer>();
		for (int i = 0; i < status.length - 1; i++) {
			String temp = status[i] + "," + status[i + 1];
			if (transitionFreq.containsKey(temp)) {
				transitionFreq.put(temp, transitionFreq.get(temp) + 1);
			} else {
				transitionFreq.put(temp, 1);
			}
		}
		return transitionFreq;
	}

	private String[] calcObservationFreq(String[] observations) {
		Hashtable<String, Integer> observationFreq = new Hashtable<String, Integer>();
		for (int i = 0; i < observations.length; i++) {
			if (observationFreq.containsKey(observations[i])) {
				observationFreq.put(observations[i], observationFreq.get(observations[i]) + 1);
			} else {
				observationFreq.put(observations[i], 1);
			}
		}
		int len = observationFreq.size();
		String[] observationType = new String[len];
		Enumeration<String> key = observationFreq.keys();
		for (int i = 0; i < observationFreq.size(); i++) {
			String str = (String) key.nextElement();
			observationType[i] = str;
			observationTypeIndex.put(str, i);
		}
		return observationType;
	}

	private String[] getAllObservation(List<String[]> observationList) {
		List<String> tempList = new ArrayList<String>();
		for (String[] ws : observationList)
			for (String s : ws)
				tempList.add(s);
		String[] observations = new String[tempList.size()];
		tempList.toArray(observations);
		return observations;
	}

	private String[] getAllStatus(List<String[]> statusList) {
		List<String> tempList = new ArrayList<String>();
		for (String[] contents : statusList)
			for (String c : contents)
				tempList.add(c);
		String[] status = new String[tempList.size()];
		tempList.toArray(status);
		return status;
	}

	private void calcStatusType(Hashtable<String, Integer> statusFreq) {
		int statusNum = statusFreq.size();
		this.statusType = new String[statusNum];
		Enumeration<String> key = statusFreq.keys();
		for (int i = 0; i < statusFreq.size(); i++) {
			statusType[i] = (String) key.nextElement();
		}
	}

}
