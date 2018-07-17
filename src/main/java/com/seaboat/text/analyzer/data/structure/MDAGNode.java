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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;

/**
 * 
 * @author basis from https://github.com/klawson88/MDAG
 * @author seaboat
 * @date 2018-07-16
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>The class which represents a node in a MDAG.</p>
 */
public class MDAGNode {
	public int id;

	//The boolean denoting the accept state status of this node
	private boolean isAcceptNode;

	//The TreeMap to containing entries that represent a transition (label and target node)
	private final TreeMap<Character, MDAGNode> outgoingTransitionTreeMap;

	//The int representing this node's incoming transition node count
	private int incomingTransitionCount = 0;

	//The int denoting index in a simplified mdag data array that this node's transition set begins at
	private int transitionSetBeginIndex = -1;

	//The int which will store this node's hash code after its been calculated (necessary due to how expensive the hashing calculation is)
	private Integer storedHashCode = null;

	/**
	 * Constructs an MDAGNode.
	 
	 * @param isAcceptNode     a boolean denoting the accept state status of this node 
	 */
	public MDAGNode(boolean isAcceptNode) {
		this.isAcceptNode = isAcceptNode;
		outgoingTransitionTreeMap = new TreeMap<Character, MDAGNode>();
	}

	/**
	 * Constructs an MDAGNode possessing the same accept state status and outgoing transitions as another.
	 
	 * @param node      the MDAGNode possessing the accept state status and 
	 *                  outgoing transitions that the to-be-created MDAGNode is to take on
	 */
	private MDAGNode(MDAGNode node) {
		isAcceptNode = node.isAcceptNode;
		outgoingTransitionTreeMap = new TreeMap<Character, MDAGNode>(node.outgoingTransitionTreeMap);

		//Loop through the nodes in this node's outgoing transition set, incrementing the number of
		//incoming transitions of each by 1 (to account for this newly created node's outgoing transitions)
		for (Entry<Character, MDAGNode> transitionKeyValuePair : outgoingTransitionTreeMap.entrySet())
			transitionKeyValuePair.getValue().incomingTransitionCount++;
		/////
	}

	public MDAGNode(boolean isAcceptNode, int id) {
		this.id = id;
		this.isAcceptNode = isAcceptNode;
		outgoingTransitionTreeMap = new TreeMap<Character, MDAGNode>();
	}

	@SuppressWarnings("unused")
	private MDAGNode(MDAGNode node, int id) {
		this.id = id;
		isAcceptNode = node.isAcceptNode;
		outgoingTransitionTreeMap = new TreeMap<Character, MDAGNode>(node.outgoingTransitionTreeMap);

		for (Map.Entry<Character, MDAGNode> transitionKeyValuePair : outgoingTransitionTreeMap.entrySet())
			transitionKeyValuePair.getValue().incomingTransitionCount++;
	}

	/**
	 * Creates an MDAGNode possessing the same accept state status and outgoing transitions as this node.
	 
	 * @return      an MDAGNode possessing the same accept state status and outgoing transitions as this node
	 */
	public MDAGNode clone() {
		return new MDAGNode(this);
	}

	/**
	 * Creates an MDAGNode possessing the same accept state status ant transition set 
	 * (incoming & outgoing) as this node. outgoing transitions as this node.
	 
	 * @param soleParentNode                        the MDAGNode possessing the only transition that targets this node
	 * @param parentToCloneTransitionLabelChar      the char which labels the transition from {@code soleParentNode} to this node
	 * @return                                      an MDAGNode possessing the same accept state status and transition set as this node. 
	 */
	public MDAGNode clone(MDAGNode soleParentNode, char parentToCloneTransitionLabelChar) {
		MDAGNode cloneNode = new MDAGNode(this);
		soleParentNode.reassignOutgoingTransition(parentToCloneTransitionLabelChar, this, cloneNode);

		return cloneNode;
	}

	/**
	 * Retrieves the index in a simplified mdag data array that the SimpleMDAGNode
	 * representation of this node's outgoing transition set begins at.
	 
	 * @return      the index in a simplified mdag data array that this node's transition set begins at,
	 *              or -1 if its transition set is not present in such an array
	 */
	public int getTransitionSetBeginIndex() {
		return transitionSetBeginIndex;
	}

	public Map.Entry<Character, MDAGNode> getLastTransition() {
		return outgoingTransitionTreeMap.lastEntry();
	}

	/**
	 * Retrieves this node's outgoing transition count.
	 
	 * @return      an int representing this node's number of outgoing transitions
	 */
	public int getOutgoingTransitionCount() {
		return outgoingTransitionTreeMap.size();
	}

	/**
	 * Retrieves this node's incoming transition count
	 
	 * @return      an int representing this node's number of incoming transitions
	 */
	public int getIncomingTransitionCount() {
		return incomingTransitionCount;
	}

	/**
	 * Determines if this node is a confluence node
	 * (defined as a node with two or more incoming transitions
	 
	 * @return      true if this node has two or more incoming transitions, false otherwise
	 */
	public boolean isConfluenceNode() {
		return (incomingTransitionCount > 1);
	}

	/**
	 * Retrieves the accept state status of this node.
	 
	 * @return      true if this node is an accept state, false otherwise
	 */
	public boolean isAcceptNode() {
		return isAcceptNode;
	}

	/**
	 * Sets this node's accept state status.
	 * 
	 * @param isAcceptNode     a boolean representing the desired accept state status 
	 */
	public void setAcceptStateStatus(boolean isAcceptNode) {
		this.isAcceptNode = isAcceptNode;
	}

	/**
	 * Records the index that this node's transition set starts at
	 * in an array containing this node's containing MDAG data (simplified MDAG).
	 
	 * @param transitionSetBeginIndex       a transition set
	 */
	public void setTransitionSetBeginIndex(int transitionSetBeginIndex) {
		this.transitionSetBeginIndex = transitionSetBeginIndex;
	}

	/**
	 * Determines whether this node has an outgoing transition with a given label.
	 
	 * @param letter        the char labeling the desired transition
	 * @return              true if this node possesses a transition labeled with
	 *                      {@code letter}, and false otherwise
	 */
	public boolean hasOutgoingTransition(char letter) {
		return outgoingTransitionTreeMap.containsKey(letter);
	}

	/**
	 * Determines whether this node has any outgoing transitions.
	 
	 * @return      true if this node has at least one outgoing transition, false otherwise
	 */
	public boolean hasOutgoingTransitions() {
		return !outgoingTransitionTreeMap.isEmpty();
	}

	/**
	 * Follows an outgoing transition of this node labeled with a given char.
	 
	 * @param letter        the char representation of the desired transition's label
	 * @return              the MDAGNode that is the target of the transition labeled with {@code letter},
	 *                      or null if there is no such labeled transition from this node
	 */
	public MDAGNode transition(char letter) {
		return outgoingTransitionTreeMap.get(letter);
	}

	/**
	 * Follows a transition path starting from this node.
	 
	 * @param str               a String corresponding a transition path in the MDAG
	 * @return                  the MDAGNode at the end of the transition path corresponding to 
	 *                          {@code str}, or null if such a transition path is not present in the MDAG
	 */
	public MDAGNode transition(String str) {
		int charCount = str.length();
		MDAGNode currentNode = this;

		//Iteratively transition through the MDAG using the chars in str
		for (int i = 0; i < charCount; i++) {
			currentNode = currentNode.transition(str.charAt(i));
			if (currentNode == null)
				break;
		}
		/////

		return currentNode;
	}

	/**
	 * Retrieves the nodes in the transition path starting
	 * from this node corresponding to a given String .
	 
	 * @param str       a String corresponding to a transition path starting from this node
	 * @return          a Stack of MDAGNodes containing the nodes in the transition path 
	 *                  denoted by {@code str}, in the order they are encountered in during transitioning
	 */
	public Stack<MDAGNode> getTransitionPathNodes(String str) {
		Stack<MDAGNode> nodeStack = new Stack<MDAGNode>();

		MDAGNode currentNode = this;
		int numberOfChars = str.length();

		//Iteratively transition through the MDAG using the chars in str,
		//putting each encountered node in nodeStack
		for (int i = 0; i < numberOfChars && currentNode != null; i++) {
			currentNode = currentNode.transition(str.charAt(i));
			nodeStack.add(currentNode);
		}
		/////

		return nodeStack;
	}

	/**
	 * Retrieves this node's outgoing transitions.
	 
	 * @return      a TreeMap containing entries collectively representing
	 *              all of this node's outgoing transitions
	 */
	public TreeMap<Character, MDAGNode> getOutgoingTransitions() {
		return outgoingTransitionTreeMap;
	}

	/**
	 * Decrements (by 1) the incoming transition counts of all of the nodes
	 * that are targets of outgoing transitions from this node.
	 */
	public void decrementTargetIncomingTransitionCounts() {
		for (Entry<Character, MDAGNode> transitionKeyValuePair : outgoingTransitionTreeMap.entrySet())
			transitionKeyValuePair.getValue().incomingTransitionCount--;
	}

	/**
	 * Reassigns the target node of one of this node's outgoing transitions.
	 
	 * @param letter            the char which labels the outgoing transition of interest
	 * @param oldTargetNode     the MDAGNode that is currently the target of the transition of interest
	 * @param newTargetNode     the MDAGNode that is to be the target of the transition of interest
	 */
	public void reassignOutgoingTransition(char letter, MDAGNode oldTargetNode, MDAGNode newTargetNode) {
		oldTargetNode.incomingTransitionCount--;
		newTargetNode.incomingTransitionCount++;

		outgoingTransitionTreeMap.put(letter, newTargetNode);
	}

	/**
	 * Creates an outgoing transition labeled with a 
	 * given char that has a new node as its target.
	 
	 * @param letter                        a char representing the desired label of the transition
	 * @param targetAcceptStateStatus       a boolean representing to-be-created transition target node's accept status
	 * @return                              the (newly created) MDAGNode that is the target of the created transition
	 */
	public MDAGNode addOutgoingTransition(char letter, boolean targetAcceptStateStatus) {
		MDAGNode newTargetNode = new MDAGNode(targetAcceptStateStatus);
		newTargetNode.incomingTransitionCount++;

		outgoingTransitionTreeMap.put(letter, newTargetNode);
		return newTargetNode;
	}

	public MDAGNode addOutgoingTransition(char letter, boolean isEndOfWord, int id) {
		MDAGNode newTargetNode = new MDAGNode(isEndOfWord, id);
		newTargetNode.incomingTransitionCount++;
		newTargetNode.id = id;

		outgoingTransitionTreeMap.put(letter, newTargetNode);
		return newTargetNode;
	}

	/**
	 * Removes a transition labeled with a given char. This only removes the connection
	 * between this node and the transition's target node; the target node is not deleted.
	 
	 * @param letter        the char labeling the transition of interest
	 */
	public void removeOutgoingTransition(char letter) {
		outgoingTransitionTreeMap.remove(letter);
	}

	/**
	 * Determines whether the sets of transition paths from two MDAGNodes are equivalent. This is an expensive operation.
	 
	 * @param outgoingTransitionTreeMap1        a TreeMap containing entries collectively representing
	 *                                          all of a node's outgoing transitions
	 * @param outgoingTransitionTreeMap2        a TreeMap containing entries collectively representing
	 *                                          all of a node's outgoing transitions
	 * @return                                  true if the set of transition paths from {@code node1}
	 *                                          and {@code node2} are equivalent
	 */
	public static boolean haveSameTransitions(MDAGNode node1, MDAGNode node2) {
		TreeMap<Character, MDAGNode> outgoingTransitionTreeMap1 = node1.outgoingTransitionTreeMap;
		TreeMap<Character, MDAGNode> outgoingTransitionTreeMap2 = node2.outgoingTransitionTreeMap;

		if (outgoingTransitionTreeMap1.size() == outgoingTransitionTreeMap2.size()) {
			//For each transition in outgoingTransitionTreeMap1, get the identically lableed transition
			//in outgoingTransitionTreeMap2 (if present), and test the equality of the transitions' target nodes
			for (Entry<Character, MDAGNode> transitionKeyValuePair : outgoingTransitionTreeMap1.entrySet()) {
				Character currentCharKey = transitionKeyValuePair.getKey();
				MDAGNode currentTargetNode = transitionKeyValuePair.getValue();

				if (!outgoingTransitionTreeMap2.containsKey(currentCharKey)
						|| !outgoingTransitionTreeMap2.get(currentCharKey).equals(currentTargetNode))
					return false;
			}
			/////
		} else
			return false;

		return true;
	}

	/**
	 * Clears this node's stored hash value
	 */
	public void clearStoredHashCode() {
		storedHashCode = null;
	}

	/**
	 * Evaluates the equality of this node with another object.
	 * This node is equal to obj if and only if obj is also an MDAGNode,
	 * and the set of transitions paths from this node and obj are equivalent.
	 
	 * @param obj       an object
	 * @return          true of {@code obj} is an MDAGNode and the set of 
	 *                  transition paths from this node and obj are equivalent
	 */
	@Override
	public boolean equals(Object obj) {
		boolean areEqual = (this == obj);

		if (!areEqual && obj != null && obj.getClass().equals(MDAGNode.class)) {
			MDAGNode node = (MDAGNode) obj;
			areEqual = (isAcceptNode == node.isAcceptNode && haveSameTransitions(this, node));
		}

		return areEqual;
	}

	/**
	 * Hashes this node using its accept state status and set of outgoing transition paths.
	 * This is an expensive operation, so the result is cached and only cleared when necessary.
	
	 * @return      an int of this node's hash code
	 */
	@Override
	public int hashCode() {

		if (storedHashCode == null) {
			int hash = 7;
			hash = 53 * hash + (this.isAcceptNode ? 1 : 0);
			hash = 53 * hash + (this.outgoingTransitionTreeMap != null ? this.outgoingTransitionTreeMap.hashCode() : 0); //recursively hashes the nodes in all the 
																															//transition paths stemming from this node
			storedHashCode = hash;
			return hash;
		} else
			return storedHashCode;
	}
}
