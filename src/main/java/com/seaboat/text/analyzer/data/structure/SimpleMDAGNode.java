/**
 * MDAG is a Java library capable of constructing character-sequence-storing,
 * directed acyclic graphs of minimal size. 
 * 
 *  Copyright (C) 2012 Kevin Lawson <Klawson88@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seaboat.text.analyzer.data.structure;

/**
 * 
 * @author basis from https://github.com/klawson88/MDAG
 * @author seaboat
 * @date 2018-07-16
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>The class capable of representing a MDAG node, its transition set, and one of its incoming transitions;
 * objects of this class are used to represent a MDAG after its been simplified in order to save space.</p>
 */

public class SimpleMDAGNode {
	//The character labeling an incoming transition to this node
	private final char letter;

	//The boolean denoting the accept state status of this node
	private final boolean isAcceptNode;

	//The int denoting the size of this node's outgoing transition set
	private final int transitionSetSize;

	//The int denoting the index (in the array which contains this node) at which this node's transition set begins
	private int transitionSetBeginIndex;

	/**
	 * Constructs a SimpleMDAGNode.
	 
	 * @param letter                a char representing the transition label leading to this SimpleMDAGNode    
	 * @param isAcceptNode          a boolean representing the accept state status of this SimpleMDAGNode
	 * @param transitionSetSize     an int denoting the size of this transition set
	 */
	public SimpleMDAGNode(char letter, boolean isAcceptNode, int transitionSetSize) {
		this.letter = letter;
		this.isAcceptNode = isAcceptNode;
		this.transitionSetSize = transitionSetSize;
		this.transitionSetBeginIndex = 0; //will be changed for all objects of this type, necessary for dummy root node creation
	}

	/**
	 * Retrieves the character representing the transition laben leading up to this node.
	 
	 * @return      the char representing the transition label leading up to this node
	 */
	public char getLetter() {
		return letter;
	}

	/**
	 * Retrieves the accept state status of this node.
	 
	 * @return      true if this node is an accept state, false otherwise
	 */
	public boolean isAcceptNode() {
		return isAcceptNode;
	}

	/**
	 * Retrieves the index in this node's containing array that its transition set begins at.
	 
	 * @return      an int of the index in this node's containing array at which its transition set begins
	 */
	public int getTransitionSetBeginIndex() {
		return transitionSetBeginIndex;
	}

	/**
	 * Retrieves the size of this node's outgoing transition set.
	 
	 * @return      an int denoting the size of this node's outgoing transition set
	 */
	public int getOutgoingTransitionSetSize() {
		return transitionSetSize;
	}

	/**
	 * Records the index in this node's containing array that its transition set begins at.
	 
	 * @param transitionSetBeginIndex       an int denoting the index in this node's containing array that is transition set beings at
	 */
	public void setTransitionSetBeginIndex(int transitionSetBeginIndex) {
		this.transitionSetBeginIndex = transitionSetBeginIndex;
	}

	/**
	 * Follows an outgoing transition from this node.
	 
	 * @param mdagDataArray     the array of SimpleMDAGNodes containing this node
	 * @param letter            the char representation of the desired transition's label
	 * @return                  the SimpleMDAGNode that is the target of the transition labeled with {@code letter},
	 *                          or null if there is no such labeled transition from this node
	 */
	public SimpleMDAGNode transition(SimpleMDAGNode[] mdagDataArray, char letter) {
		int onePastTransitionSetEndIndex = transitionSetBeginIndex + transitionSetSize;
		SimpleMDAGNode targetNode = null;

		//Loop through the SimpleMDAGNodes in this node's transition set, searching for
		//the one with a letter equal to that which labels the desired transition
		for (int i = transitionSetBeginIndex; i < onePastTransitionSetEndIndex; i++) {
			if (mdagDataArray[i].getLetter() == letter) {
				targetNode = mdagDataArray[i];
				break;
			}
		}
		/////

		return targetNode;
	}

	/**
	 * Follows a transition path starting from this node.
	 
	 * @param mdagDataArray     the array of SimpleMDAGNodes containing this node
	 * @param str               a String corresponding a transition path in the MDAG
	 * @return                  the SimpleMDAGNode at the end of the transition path corresponding to 
	 *                          {@code str}, or null if such a transition path is not present in the MDAG
	 */
	public SimpleMDAGNode transition(SimpleMDAGNode[] mdagDataArray, String str) {
		SimpleMDAGNode currentNode = this;
		int numberOfChars = str.length();

		//Iteratively transition through the MDAG using the chars in str
		for (int i = 0; i < numberOfChars; i++) {
			currentNode = currentNode.transition(mdagDataArray, str.charAt(i));
			if (currentNode == null)
				break;
		}
		/////

		return currentNode;
	}

	/**
	 * Follows a transition path starting from the source node of a MDAG.
	 
	 * @param mdagDataArray     the array containing the data of the MDAG to be traversed
	 * @param sourceNode        the dummy SimpleMDAGNode which functions as the source of the MDAG data in {@code mdagDataArray}
	 * @param str               a String corresponding to a transition path in the to-be-traversed MDAG
	 * @return                  the SimpleMDAGNode at the end of the transition path corresponding to 
	 *                          {@code str}, or null if such a transition path is not present in the MDAG
	 */
	public static SimpleMDAGNode traverseMDAG(SimpleMDAGNode[] mdagDataArray, SimpleMDAGNode sourceNode, String str) {
		char firstLetter = str.charAt(0);

		//Loop through the SimpleMDAGNodes in the processing MDAG's source node's transition set, 
		//searching for the the one with a letter (char) equal to the first char of str.
		//We can use that target node to transition through the MDAG with the rest of the string
		for (int i = 0; i < sourceNode.transitionSetSize; i++) {
			if (mdagDataArray[i].getLetter() == firstLetter)
				return mdagDataArray[i].transition(mdagDataArray, str.substring(1));
		}
		/////

		return null;
	}
}