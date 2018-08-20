package com.seaboat.text.analyzer.data.structure;

/**
 * 
 * @author seaboat
 * @date 2018-08-20
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the graph for Dijkstra algorithm.</p>
 */
public class Graph {

	private Vertex[] vertexes = null;

	public Graph(Vertex[] vertexes, Edge[] edges) {
		this.vertexes = vertexes;
	}

	public Vertex[] getVertexes() {
		return this.vertexes;
	}

}