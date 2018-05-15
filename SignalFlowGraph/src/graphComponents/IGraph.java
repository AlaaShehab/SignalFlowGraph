package graphComponents;

public interface IGraph {
	
	boolean addEdge(IEdge edge);
	boolean removeEdge(IEdge edge);
	double[][] getGraph ();

}
