package graphDrawingEngine;
import graphComponents.Edge;
import graphComponents.Graph;
import graphComponents.IEdge;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		Draw frame = new Draw();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 620);
		frame.setVisible(true);
	}

}
