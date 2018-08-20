package com.seaboat.text.analyzer.data.structure;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-08-20
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Dijkstra tester.</p>
 */
public class DijkstraTest {

	@Test
	public void createAndDeleteTree() {

		String[] ss = { "1-2", "1-3", "1-4", "2-4", "3-5", "4-6", "4-7", "5-7", "6-7" };

		Vertex[] vertices = new Vertex[7];
		for (int i = 0; i < 7; i++)
			vertices[i] = new Vertex(String.valueOf(i + 1));

		Edge[] edges = new Edge[ss.length];
		for (int i = 0; i < ss.length; i++) {
			String[] pos = ss[i].split("-");
			edges[i] = new Edge(1, vertices[Integer.parseInt(pos[0]) - 1], vertices[Integer.parseInt(pos[1]) - 1]);
			vertices[Integer.parseInt(pos[0]) - 1].addEdge(edges[i]);
			vertices[Integer.parseInt(pos[1]) - 1].addEdge(edges[i]);
		}

		Graph graph = new Graph(vertices, edges);

		Dijkstra dijkstra = new Dijkstra();
		Vertex begin = dijkstra.getVertexByName(graph, "1");
		Vertex end = dijkstra.getVertexByName(graph, "7");
		System.out.println(dijkstra.getShortestPath(graph, begin, end));
		dijkstra.printGraph(graph);
	}

}
