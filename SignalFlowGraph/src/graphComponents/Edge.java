package graphComponents;

public class Edge implements IEdge{
	
	private Double gain;
	private int fromNode;
	private int toNode;

	public void setFromNode(int fromNode) {
		this.fromNode = fromNode;

	}

	public int getFromNode() {
		return fromNode;
	}

	public void setToNode(int toNode) {
		this.toNode = toNode;
	}

	public int getToNode() {
		return toNode;
	}

	public void setGain(Double gain) {
		this.gain = gain;
		
	}

	public Double getGain() {
		return gain;
	}

}
