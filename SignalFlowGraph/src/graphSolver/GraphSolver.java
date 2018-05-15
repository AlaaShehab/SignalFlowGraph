/**
 *
 */
package graphSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import graphComponents.IGraph;
import graphComponents.IPath;
import graphComponents.Path;

/**
 * @author Personal
 *
 */
public class GraphSolver implements IGraphSolver {

	private IGraph graph;
	private double[][] graphMatrix;
	private boolean[] visited;
	private List<IPath> forwardPaths;
	private List<IPath> loops;
	private List<HashSet<Integer>> nonTouchingLoopsVertices;
	private List<List<StringBuilder>> nonTouchingLoops;
	private double graphDelta;
	private double transferFunctionGain;

	public GraphSolver(IGraph graph) {
		this.graph = graph;
		graphMatrix = graph.getGraph();
		forwardPaths = new ArrayList<IPath>();
		loops = new ArrayList<IPath>();
		nonTouchingLoopsVertices = new ArrayList<HashSet<Integer>>();
		nonTouchingLoops = new ArrayList<List<StringBuilder>>();
		graphDelta = 0.0;
		transferFunctionGain = 0.0;
	}

	@Override
	public void solveForwardPathes() {
		visited = new boolean[graphMatrix.length];
		IPath path = new Path();
		path.addNode(1);
		getPath(1, graphMatrix.length - 1, path, forwardPaths);
	}

	@Override
	public void solveLoops() {
		visited = new boolean[graphMatrix.length];
		for (int i = 1; i < graphMatrix.length; i++) {
			IPath path = new Path();
			path.addNode(i);
			getLoop(i, i, path, loops);
		}
	}

	private void initializeLists() {
		List<StringBuilder> list = new ArrayList<StringBuilder>();
		for (int i = 0; i < loops.size(); i++) {
			HashSet<Integer> set = new HashSet<Integer>();
			set.addAll(loops.get(i).getNodes());
			nonTouchingLoopsVertices.add(set);
			StringBuilder oneNonTouchLoop = new StringBuilder(String.valueOf(i));
			list.add(oneNonTouchLoop);
		}
		nonTouchingLoops.add(list);
	}

	@Override
	public void solveNonTouchingLoops () {
		initializeLists();
		boolean solving = solveiTouchingLoops();
		while (solving) {
			solving = solveiTouchingLoops();
		}
	}

	private boolean solveiTouchingLoops() {
		List<StringBuilder> listOfNnonTouchLoop = new ArrayList<StringBuilder>();
		List<Integer> loopsVertices;

		int level = nonTouchingLoops.size() - 1;//how many non touching loops I'm looking for and -1 to get their array index
		boolean loopsMatch = false; //to know if there's a match between n loops
		int i = 0;
		int nonTouchingLoopsSize = nonTouchingLoopsVertices.size(); //loop on size of all N non touching loops

		while (i < nonTouchingLoopsSize) {
			for (int j = i + 1; j < loops.size(); j++) {

				loopsVertices = new ArrayList<Integer>();
				List<Integer> vertices = loops.get(j).getNodes();
				loopsMatch = false;

 				for (int k = 0; k < vertices.size(); k++) {
 					loopsVertices.add(vertices.get(k));
 					if (nonTouchingLoopsVertices.get(0).contains(vertices.get(k))) {
 						loopsMatch = true;
 						break;
 					}
				}
 				if (!loopsMatch) {
 					HashSet<Integer> set = new HashSet<Integer>();
 					set.addAll(loopsVertices);
 					set.addAll(nonTouchingLoopsVertices.get(0));

 					StringBuilder nNonTouchLoop = new StringBuilder(nonTouchingLoops.get(level).get(i));
 					nNonTouchLoop.append(String.valueOf(j));

 					char tempArray[] = (nNonTouchLoop.toString()).toCharArray();
 			        Arrays.sort(tempArray);
 			        nNonTouchLoop = new StringBuilder(new String(tempArray));

 			        if (!contains(nNonTouchLoop, listOfNnonTouchLoop)) {
 	 					nonTouchingLoopsVertices.add(set);
 	 			        listOfNnonTouchLoop.add(nNonTouchLoop);
 			        }
 				}
			}
			nonTouchingLoopsVertices.remove(0);
			i++;
		}
		if (!listOfNnonTouchLoop.isEmpty()) {
			nonTouchingLoops.add(listOfNnonTouchLoop);
			return true;
		}
		return false;
	}

	private boolean contains(StringBuilder string, List<StringBuilder> list) {
		for (int i = 0; i < list.size(); i++) {
			if ((list.get(i).toString()).equals(string.toString())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public double getForwardPathDelta(int forwardPath) {
		double result = 1;
		for (int i = 0; i < nonTouchingLoops.size(); i++) {
			double gain1 = 0;
			//loop on let's say 2 non touching loop first
			for (int j = 0; j < nonTouchingLoops.get(i).size(); j++) {
				StringBuilder nonTouchLoop = nonTouchingLoops.get(i).get(j);
				double gain2 = 1;
				//loop on all loops in this 2 non touching loop arrangement
				for (int k = 0; k < nonTouchLoop.length(); k++) {
					int loopNumber = nonTouchLoop.charAt(k) - 48;
					if (touching(loopNumber, forwardPath)) {
						gain2 = 0;
					}
					gain2 *= loops.get(loopNumber).getGain();
				}
				gain1 += gain2;
			}
			result += (Math.pow(-1, i + 1))*gain1;
		}
		return result;
	}

	private boolean touching(int loop, int forwardPath) {
		HashSet<Integer> setForwardPath = new HashSet<Integer>();
		HashSet<Integer> setLoop = new HashSet<Integer>();
		HashSet<Integer> setTotal = new HashSet<Integer>();
		setForwardPath.addAll(forwardPaths.get(forwardPath).getNodes());
		setLoop.addAll(loops.get(loop).getNodes());

		setTotal.addAll(setForwardPath);
		setTotal.addAll(setLoop);

		if (setTotal.size() != setForwardPath.size() + setLoop.size()) {
			return true;
		}
		return false;
	}

	@Override
	public double solveGraphDelta() {
		double result = 1;
		for (int i = 0; i < nonTouchingLoops.size(); i++) {
			double gain1 = 0;
			for (int j = 0; j < nonTouchingLoops.get(i).size(); j++) {
				StringBuilder nonTouchLoop = nonTouchingLoops.get(i).get(j);
				double gain2 = 1;
				for (int k = 0; k < nonTouchLoop.length(); k++) {
					int loopNumber = nonTouchLoop.charAt(k) - 48;
					gain2 *= loops.get(loopNumber).getGain();
				}

				gain1 += gain2;
			}
			result += (Math.pow(-1, i + 1))*gain1;
		}
		graphDelta = result;
		return result;
	}

	@Override
	public double getForwardPathGain(int forwardPath) {
		return forwardPaths.get(forwardPath).getGain();
	}

	@Override
	public boolean isGraphConnected() {
		for (int i = 1; i < graphMatrix.length - 1; i++) {
			if (graphMatrix[i][i + 1] != 1) {
				return false;
			}
		}
		return true;
	}

	@Override
	public double solveTransferFunction() {
		double sigma = 0.0;
		for (int i = 0; i < forwardPaths.size(); i++) {
			sigma += (getForwardPathDelta(i) * getForwardPathGain(i));
		}
		transferFunctionGain = (sigma / solveGraphDelta());
		return transferFunctionGain;
	}

	@Override
	public void newGraph(IGraph graph) {
		this.graph = graph;
		graphMatrix = graph.getGraph();
		visited = new boolean[graphMatrix.length];
		forwardPaths = new ArrayList<IPath>();
		loops = new ArrayList<IPath>();
		nonTouchingLoopsVertices = new ArrayList<HashSet<Integer>>();
		nonTouchingLoops = new ArrayList<List<StringBuilder>>();
		graphDelta = 0.0;
		transferFunctionGain = 0.0;
	}

	private void getPath (int start, int end, IPath path, List<IPath> pathes) {
		visited[start] = true;
		if (start == end) {
			IPath pathi = path.clone(path);
			pathes.add(pathi);
		}

		for (int i = 1; i < graphMatrix.length; i++) {
			if (graphMatrix[start][i] != 0 && !visited[i]) {
				if (i - start > 1) {
					for (int j = start + 1; j < i; j++) {
						visited[j] = true;
					}
				}
				path.addNode(i, graphMatrix[start][i]);
				getPath(i, end, path, pathes);
				path.removeNode(i, graphMatrix[start][i]);
			}
		}
		visited[start] = false;
	}

	private void getLoop (int start, int end, IPath path, List<IPath> pathes) {

		if (start == end && visited[start]) {
			IPath pathi = path.clone(path);
			pathes.add(pathi);
		}

		for (int i = 1; i < graphMatrix.length; i++) {
			if (graphMatrix[start][i] != 0 && !visited[i]
					&& (i >= end)) {
				visited[i] = true;
				path.addNode(i, graphMatrix[start][i]);
				getLoop(i, end, path, pathes);
				path.removeNode(graphMatrix[start][i]);
			}
		}
		visited[start] = false;
	}

	@Override
	public List<IPath> getForwardPathes() {
		return forwardPaths;
	}

	@Override
	public List<IPath> getLoops() {
		return loops;
	}

	@Override
	public List<List<StringBuilder>> getNonTouchingLoops() {
		return nonTouchingLoops;
	}

	@Override
	public double getGraphDelta() {
		return graphDelta;
	}

	@Override
	public double getTransferFunction() {
		return transferFunctionGain;
	}
}

