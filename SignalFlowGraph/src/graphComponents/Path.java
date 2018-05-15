package graphComponents;

import java.util.ArrayList;
import java.util.List;

public class Path implements Cloneable, IPath{

	private List<Integer> nodes;
	private double gain;
	private int pathSize;

	public Path() {
		nodes = new ArrayList<Integer>();
		gain = 1.0;
		pathSize = 0;
	}
	@Override
	public void addNode(int node, double gain) {
		nodes.add(node);
		this.gain *= gain;
		pathSize++;
	}

	@Override
	public void addNode(int node) {
		nodes.add(node);
		pathSize++;
	}

	@Override
	public void removeNode(int node, double gain) {
		if (nodes.contains(node)) {
			Object o = node;
			nodes.remove(o);
			this.gain /= gain;
			pathSize--;
		}
	}
	@Override
	public void removeNode(double gain) {
		nodes.remove(nodes.size() - 1);
		this.gain /= gain;
		pathSize--;
	}

	@Override
	public void setGain(double gain) {
		this.gain = gain;

	}

	@Override
	public List<Integer> getNodes() {
		return nodes;
	}
	@Override
	public double getGain() {
		return gain;
	}
	@Override
	public IPath clone(IPath path) {
		IPath newPath = new Path();
		for (int i = 0; i < pathSize; i++){
			newPath.addNode(path.getNodes().get(i));
		}
		newPath.setGain(path.getGain());
		return newPath;
	}
	@Override
	public int getPathSize() {
		return pathSize;
	}

}
