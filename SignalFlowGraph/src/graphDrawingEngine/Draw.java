package graphDrawingEngine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.commons.lang.math.NumberUtils;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import graphComponents.Edge;
import graphComponents.Graph;
import graphComponents.IEdge;
public class Draw extends JFrame {

	private int numOfNodes;
	private int addedEdges;

	private mxGraph graph;
	private Object parent;
	private Graph g;
	private mxGraphComponent graphComponent;
	private JButton nodes;
	private JButton newGraph;
	private JButton addEdge;
	private JButton done;
	private JTextField fromNode;
	private JTextField nodesNum;
	private JTextField toNode;
	private JTextField gain;
	private boolean firstTime;
	private JMenuBar menuBar;

	public JPanel buttons = new JPanel();
	private Box box1;
	private Box box2;
	private Box box3;

	public Draw () {
		firstTime = true;
		numOfNodes = 0;
		addedEdges = 0;
		GUI ();
	}

	private void PaintGraph(){
		if (!firstTime){
			super.getContentPane().remove(graphComponent);
		}
		graph = new mxGraph();
		graph.setAllowDanglingEdges(false);
		parent = graph.getDefaultParent();
		numOfNodes = g.getGraph().length;
		graph.getModel().beginUpdate();

		int x = 20;
		int y = 20;
		Object [] temp = new Object[g.getGraph().length - 1];
		for (int i = 1; i < numOfNodes ; i++) {
				String str = Integer.toString(i);
				temp[i - 1] = graph.insertVertex(parent, null, str, x, y, 80,30,"shape=ellipse;perimeter=ellipsePerimeter;fillColor=BLUE;fontColor=WHITE");
				x+=100;
				y+=30;
			}
			for (int i = 1; i < numOfNodes; i ++) {
				for (int j = 1; j < numOfNodes; j++){
					if(g.getGraph()[i][j] != 0) {
						String v1 = Integer.toString(i);
						String v2 = Integer.toString(j);
						graph.insertEdge(parent, null, g.getGraph()[i][j], temp[i-1], temp[j-1],"curved=1;rounded=1;edgeStyle=segmentEdgeStyle;segment=15;labelPosition=left;fontColor=#00000;strokeColor=#000000;verticalAlign=bottom;strokeWidth=1;strokeLength=0.1");
					}
				}
			}
			
		mxConstants.LINESPACING = 112;
		graph.getModel().endUpdate();
	    graphComponent = new mxGraphComponent(graph);
	    graphComponent.getViewport().setOpaque(true);
	    graphComponent.getViewport().setBackground(Color.WHITE);
	    graphComponent.setPreferredSize(new Dimension(1200,1200));;
		super.getContentPane().add(graphComponent);
		super.getContentPane().revalidate();
		super.getContentPane().repaint();
	}

	private void GUI (){

		setLayout();
		nodesNum = new JTextField();
		nodes = new JButton("Enter #nodes");
	    box1.add(Box.createRigidArea(new Dimension(100, 0)));
		box1.add(nodesNum);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));
		box1.add(nodes);
	    box1.add(Box.createRigidArea(new Dimension(5, 0)));


		fromNode = new JTextField();
		toNode = new JTextField();
		gain = new JTextField();
		addEdge = new JButton("Add Edge");
	    box2.add(Box.createRigidArea(new Dimension(100, 0)));
		box2.add(fromNode);
	    box2.add(Box.createRigidArea(new Dimension(5, 0)));
		box2.add(toNode);
	    box2.add(Box.createRigidArea(new Dimension(5, 0)));
		box2.add(gain);
	    box2.add(Box.createRigidArea(new Dimension(5, 0)));
		box2.add(addEdge);

		newGraph = new JButton("New Graph");
		done = new JButton("Done");
		box3.add(done);
	    box3.add(Box.createRigidArea(new Dimension(30, 0)));
		box3.add(newGraph);

		super.getContentPane().add(buttons, BorderLayout.NORTH);
		initializeComponents();

		
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem addGraph = new JMenuItem("Add Graph");
		menu.add(addGraph);
		super.setJMenuBar(menuBar);

		final JFrame frame = this;
		addGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Open Graph File");
	    		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
	    			File file = fileChooser.getSelectedFile();
	    			String[] splitter = file.getPath().split("\\.");
	    			String extension = splitter[splitter.length - 1];
	    			if (!extension.equals("txt")) {
						JOptionPane.showMessageDialog(null, "File extension is not supported");
						return;
	    			}
	    			readFile(file.getPath());
	    		}

			}

			private void readFile(String fileName) {
				FileReader file;
				BufferedReader readFile = null;
				String line = null;
				try {
					file = new FileReader(fileName);
					readFile = new BufferedReader(file);
					line = readFile.readLine();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error In reading file");
					return;
				}
				int numberOfNode;
				try {
					numberOfNode = Integer.parseInt(line);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid Number of Nodes");
					return;
				}
				if (numberOfNode < 2) {
					JOptionPane.showMessageDialog(null, "Invalid Number of Nodes");
				}
				g = new Graph(numberOfNode);
				numOfNodes = numberOfNode;

				addedEdges = 0;
				try {
					while ((line = readFile.readLine()) != null) {

						String[] temp = line.split(" ");
						IEdge edge = new Edge();
						edge.setFromNode(Integer.parseInt(temp[0]));
						edge.setToNode(Integer.parseInt(temp[1]));
						edge.setGain(Double.parseDouble(temp[2]));
						g.addEdge(edge);

						if (Integer.parseInt(temp[0]) < 0 || Integer.parseInt(temp[0]) > numberOfNode
								|| Integer.parseInt(temp[1]) < 0 || Integer.parseInt(temp[1]) > numberOfNode) {
							JOptionPane.showMessageDialog(null, "Invalid Data Input");
							return;
						}
						addedEdges++;
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Invalid Data Input");
				}
				newGraph.setEnabled(true);
				done.setEnabled(true);

			}
		});

		nodesNum.setPreferredSize(new Dimension(100,21));
		nodesNum.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				if	(nodesNum.getText().equals("nodesNum")) {
					nodesNum.setText("");
				}
			}
		});

		nodes.setPreferredSize(new Dimension(150,21));
		nodes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = nodesNum.getText();
				boolean valid = isValid(temp);
				if (valid) {
					numOfNodes = Integer.parseInt(temp);
					g = new Graph(numOfNodes);
					addEdge.setEnabled(true);
					nodes.setEnabled(false);
					newGraph.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Input Must be a number greater than 1");
				}
			}

			private boolean isValid(String temp) {
				if (temp == null || temp == "") {
					return false;
				}
				boolean isNumeric = NumberUtils.isNumber(temp);
				if (!isNumeric  || temp.contains(".") || Integer.parseInt(temp) < 2) {
					return false;
				}
				return true;
			}
		});

		fromNode.setPreferredSize(new Dimension(100,21));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		fromNode.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if	(fromNode.getText().equals("fromNode")) {
					fromNode.setText("");
				}
			}
		});

		toNode.setPreferredSize(new Dimension(100,21));
		setLayout(new FlowLayout(FlowLayout.CENTER));
		toNode.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusGained(FocusEvent arg0) {
				if	(toNode.getText().equals("toNode")) {
					toNode.setText("");
				}
			}
		});

		gain.setPreferredSize(new Dimension(100,21));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		gain.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				if	(gain.getText().equals("gain")) {
					gain.setText("");
				}
			}
		});

		addEdge.setPreferredSize(new Dimension(100,21));
		addEdge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid = isValid(fromNode.getText(), toNode.getText(), gain.getText());

				if (!valid) {
					JOptionPane.showMessageDialog(null, "Node must be smaller than " + numOfNodes);
					return;
				}
				addedEdges++;
				IEdge edge = new Edge();
				edge.setFromNode(Integer.parseInt(fromNode.getText()));
				edge.setToNode(Integer.parseInt(toNode.getText()));
				edge.setGain(Double.parseDouble(gain.getText()));
				g.addEdge(edge);
				done.setEnabled(true);
			}

			private boolean isValid(String fromNode, String toNode, String gain) {

				boolean fromNodeIsNum = NumberUtils.isNumber(fromNode);
				boolean toNodeIsNum = NumberUtils.isNumber(toNode);
				if (gain.length() >= 2 && gain.charAt(0) == '-') {
					gain = gain.substring(1);
				}
				boolean gainIsNum = NumberUtils.isNumber(gain);

				if (fromNodeIsNum && toNodeIsNum && gainIsNum
						&& !fromNode.contains(".") && !toNode.contains(".")
						&& Integer.parseInt(fromNode) > 0 && Integer.parseInt(fromNode) <= numOfNodes
						&& Integer.parseInt(toNode) > 0 && Integer.parseInt(toNode) <= numOfNodes) {
					return true;
				}
				return false;
			}
		});


		done.setPreferredSize(new Dimension(100,21));
		done.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (addedEdges < numOfNodes - 1) {
					JOptionPane.showMessageDialog(null, "Graph is Disconnected");
					return;
				}
				PaintGraph();
				firstTime = false;
				createFrame();
			}
		});

		newGraph.setLocation(300,300);
		newGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!firstTime){
					getContentPane().remove(graphComponent);
				}
				initializeComponents();
				g = null;
				getContentPane().revalidate();
				getContentPane().repaint();
			}
		});
	}

	private void setLayout() {
		box1 = Box.createHorizontalBox();
		box2 = Box.createHorizontalBox();
		box3 = Box.createHorizontalBox();

		BoxLayout boxLayout1 = new BoxLayout(buttons, BoxLayout.Y_AXIS);
		buttons.setLayout(boxLayout1);

		buttons.add(box1);
		buttons.add(Box.createRigidArea(new Dimension(0,5)));
		buttons.add(box2);
		buttons.add(Box.createRigidArea(new Dimension(0,5)));
		buttons.add(box3);
		buttons.add(Box.createRigidArea(new Dimension(0,5)));
	}

	private void initializeComponents() {
		addEdge.setEnabled(false);
		done.setEnabled(false);
		nodes.setEnabled(true);
		newGraph.setEnabled(false);
		nodesNum.setText("nodesNum");
		fromNode.setText("fromNode");
		gain.setText("gain");
		toNode.setText("toNode");
		numOfNodes = 0;
		addedEdges = 0;

	}

	public void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Table");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		frame.setSize(700, 500);
        		frame.setVisible(true);
                try
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }

                PathsTable table = new PathsTable(frame,g);
                table.fillTable();
            }
        });
    }
}

