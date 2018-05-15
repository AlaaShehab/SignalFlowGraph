package graphComponents;

public interface IEdge {
	void setFromNode(int fromNode);
	int getFromNode();
	void setToNode(int toNode);
	int getToNode();
	void setGain(Double gain);
	Double getGain();

}
