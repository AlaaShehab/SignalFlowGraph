package graphComponents;

public class Graph implements IGraph{
	
	private int numOfNodes;
	private double[][]graph;
	
	public Graph(int numOfNodes){
		this.numOfNodes = numOfNodes;
		graph = new double[numOfNodes + 1][numOfNodes + 1];
	}

	public boolean addEdge(IEdge edge) {
		if (graph[edge.getFromNode()][edge.getToNode()] != 0){
			return false;
		}
		graph[edge.getFromNode()][edge.getToNode()] = edge.getGain();
		return true;
	}

	public boolean removeEdge(IEdge edge) {
		if (graph[edge.getFromNode()][edge.getToNode()] == 0){
			return false;
		}
		graph[edge.getFromNode()][edge.getToNode()] = 0.0;
		return true;
	}
	
	public double[][] getGraph () {
		return graph;
	}
	

}
