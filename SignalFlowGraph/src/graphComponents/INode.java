/**
 *
 */
package graphComponents;

import java.util.List;

/**
 * @author alaa
 *
 */
public interface INode {

	String getNodeName();
	List<IEdge> getInEdges();
	List<IEdge> getOutEdges();
	void setNodeName(String name);
	void addInEdge(IEdge edge);
	void removeInEdge(IEdge edge);
	void addOutEdge(IEdge edge);
	void removeOutEdge(IEdge edge);




}
