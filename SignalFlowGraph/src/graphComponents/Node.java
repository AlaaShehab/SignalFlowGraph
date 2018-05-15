/**
 *
 */
package graphComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Personal
 *
 */
public class Node implements INode {

	String nodeName;
	List<IEdge> inEdges;
	List<IEdge> outEdges;

	public Node () {
		inEdges = new ArrayList<IEdge>();
		outEdges = new ArrayList<IEdge>();
	}

	public String getNodeName() {
		return nodeName;
	}

	public List<IEdge> getInEdges() {
		return inEdges;
	}

	public List<IEdge> getOutEdges() {
		return outEdges;
	}
	public void addInEdge(IEdge edge) {
		if (!inEdgeExists(edge)) {
			inEdges.add(edge);
		}
      throw new RuntimeException("Edge already exists");
	}

	public void removeInEdge(IEdge edge) {
		if (!inEdgeExists(edge)) {
			inEdges.remove(edge);
		}
      throw new RuntimeException("Edge doesn't exist");

	}

	public void addOutEdge(IEdge edge) {
		if (!outEdgeExists(edge)) {
			outEdges.add(edge);
		}
      throw new RuntimeException("Edge already exists");
	}

	public void removeOutEdge(IEdge edge) {
		if (!outEdgeExists(edge)) {
			outEdges.remove(edge);
		}
      throw new RuntimeException("Edge doesn't exist");

	}
	public void setNodeName(String name) {
		nodeName = name;
	}

	private boolean inEdgeExists(IEdge edge1) {
		for (int i = 0; i < inEdges.size(); i++) {
			if (edge1.equals(inEdges.get(i))) {
				return true;
			}
		}
		return false;
	}

	private boolean outEdgeExists(IEdge edge1) {
		for (int i = 0; i < outEdges.size(); i++) {
			if (edge1.equals(outEdges.get(i))) {
				return true;
			}
		}
		return false;
	}


}
