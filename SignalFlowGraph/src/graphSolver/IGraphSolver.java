/**
 *
 */
package graphSolver;

import java.util.List;

import graphComponents.IGraph;
import graphComponents.IPath;

/**
 * @author Personal
 *
 */
public interface IGraphSolver {

	List<IPath> getForwardPathes();
	List<IPath> getLoops();
	List<List<StringBuilder>> getNonTouchingLoops();
	double getGraphDelta();
	double getTransferFunction();
	double getForwardPathDelta(int forwardPath);
	double getForwardPathGain(int forwardPath);
	boolean isGraphConnected();
	void newGraph(IGraph graph);


	void solveForwardPathes();
	void solveLoops();
	void solveNonTouchingLoops();
	double solveGraphDelta();
	double solveTransferFunction();

}
