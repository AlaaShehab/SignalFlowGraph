package graphComponents;

import java.util.List;

public interface IPath {
	void addNode(int node, double gain);
	void addNode(int node);
	void removeNode(int node, double gain);
	void removeNode(double gain);
	void setGain(double gain);
	List<Integer> getNodes();
	double getGain();
	IPath clone(IPath path);
	int getPathSize();
}
