package com.seaboat.text.analyzer.data.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-08-20
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the vertex for Dijkstra algorithm.</p>
 */
public class Vertex {

	private boolean isVisited = false;

	private String vertexName = null;

	private List<Edge> toEdges = new ArrayList<Edge>();

	public Vertex(String vertexName) {
		this.vertexName = vertexName;
	}

	public void addEdge(Edge toEdge) {
		this.toEdges.add(toEdge);
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited() {
		this.isVisited = true;
	}

	public List<Edge> getToEdges() {
		return this.toEdges;
	}

	public String getVertexName() {
		return this.vertexName;
	}

}
