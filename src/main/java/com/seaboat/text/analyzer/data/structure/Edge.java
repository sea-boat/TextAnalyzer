package com.seaboat.text.analyzer.data.structure;

/**
 * 
 * @author seaboat
 * @date 2018-08-20
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the edge for Dijkstra algorithm.</p>
 */
public class Edge {

	private boolean isVisited = false;

	private Integer weight = null;

	private Vertex fromVertex = null;

	private Vertex toVertex = null;

	public Edge(int weight, Vertex fromVertex, Vertex toVertex) {
		this.weight = weight;
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
	}

	public Integer getWeight() {
		return weight;
	}

	public Vertex getFromVertex() {
		return fromVertex;
	}

	public Vertex getToVertex() {
		return toVertex;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited() {
		isVisited = true;
	}
}
