package graphDrawingEngine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import graphComponents.Graph;
import graphComponents.IPath;
import graphSolver.GraphSolver;
import graphSolver.IGraphSolver;

/**
 * @author Personal
 *
 */
public class PathsTable {
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private Graph graph;
	private IGraphSolver solveGraph;
	private JLabel transferFuncLlabel;

	public PathsTable(JFrame frame, Graph graph){
		this.frame = frame;
		this.graph = graph;
		solveGraph = new GraphSolver(graph);
		initialize();
	}

	private void initialize() {
	    model = new DefaultTableModel();
		table = new JTable(model);
		transferFuncLlabel = new JLabel("");
		transferFuncLlabel.setPreferredSize(new Dimension(120,50));
		frame.getContentPane().add(transferFuncLlabel, BorderLayout.NORTH);
		model.addColumn("Type");
		model.addColumn("Path/Loops");
		model.addColumn("Gain");
		frame.add(new JScrollPane(table));

	}

	public void fillTable() {
		//check if graph is connected first
		solveGraph.solveForwardPathes();
		solveGraph.solveLoops();
		solveGraph.solveNonTouchingLoops();
		solveGraph.solveGraphDelta();


		List<IPath> forwardPaths = solveGraph.getForwardPathes();
		for (int i = 0; i < forwardPaths.size(); i++) {
			Object[] rowData = new StringBuffer[3];
			IPath path = forwardPaths.get(i);
			List<Integer> nodes = path.getNodes();

			StringBuffer row = new StringBuffer();
			for (int j = 0; j < path.getPathSize(); j++) {
				row.append(nodes.get(j));
			}
			rowData[1] = row;

			row = new StringBuffer("Forward Path '");
			row.append(i+1).append("'");
			rowData[0] = row;

			row = new StringBuffer();
			row.append(path.getGain());
			rowData[2] = row;
			model.addRow(rowData);
		}

		List<IPath> loops = solveGraph.getLoops();
		for (int i = 0; i < loops.size(); i++) {
			Object[] rowData = new StringBuffer[3];
			IPath path = loops.get(i);
			List<Integer> nodes = path.getNodes();
			StringBuffer row = new StringBuffer();

			for (int j = 0; j < path.getPathSize(); j++) {
				row.append(nodes.get(j));
			}
			rowData[1] = row;

			row = new StringBuffer("Loop '");
			row.append(i+1).append("'");
			rowData[0] = row;

			row = new StringBuffer();
			row.append(path.getGain());
			rowData[2] = row;
			model.addRow(rowData);
		}

		List<List<StringBuilder>> nonTouchingLoops = solveGraph.getNonTouchingLoops();
		for (int i = 1; i < nonTouchingLoops.size(); i++) {
			for (int j = 0; j < nonTouchingLoops.get(i).size(); j++) {
				Object[] rowData = new StringBuilder[3];

				StringBuilder row = new StringBuilder();
				double gain = 1;

				StringBuilder nLoop = nonTouchingLoops.get(i).get(j);
				for (int k = 0; k < nLoop.length(); k++) {
					row.append("Loop '").append(nLoop.charAt(k) - 48 + 1).append("' ");
					int loop = nLoop.charAt(k) - 48;
					gain *= (loops.get(loop)).getGain();
				}
				rowData[1] = row;

				row = new StringBuilder();
				row.append(i+1).append(" Non Touching Loops");
				rowData[0] = row;

				row = new StringBuilder();
				row.append(gain);
				rowData[2] = row;
				model.addRow(rowData);
			}
		}

		transferFuncLlabel.setText("Transfer Function Gain= " + solveGraph.solveTransferFunction());
	}
}
