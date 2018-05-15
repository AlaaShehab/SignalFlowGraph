package graphSolver;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.cycle.*;

public class Main {
	
	public static void main (String[] args){
		
	DirectedGraph g = new  DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
	String v1 = "v1";
    String v2 = "v2";
    String v3 = "v3";
    String v4 = "v4";

    // add the vertices
    g.addVertex(v1);
    g.addVertex(v2);
    g.addVertex(v3);
    g.addVertex(v4);
    
    g.addEdge(v1, v2);
    g.addEdge(v2, v3);
    g.addEdge(v3, v4);
    g.addEdge(v4, v1);
    g.addEdge(v1, v3);
    g.addEdge(v2, v1);
    List<List<String>> list1 = (new  TarjanSimpleCycles<String,DefaultEdge> (g)).findSimpleCycles();
    List<GraphPath<String, DefaultEdge>> list = (new AllDirectedPaths<String, DefaultEdge>(g)).getAllPaths(v1, v4, true, null);
    System.out.println(list);
    System.out.println(list1);
	
	}
}
