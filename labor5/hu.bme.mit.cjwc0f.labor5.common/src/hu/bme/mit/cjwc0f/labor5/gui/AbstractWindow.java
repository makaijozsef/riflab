package hu.bme.mit.cjwc0f.labor5.gui;

import hu.bme.mit.cjwc0f.labor5.workflow.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public abstract class AbstractWindow extends JFrame {

	protected JTextArea textArea;
	protected JButton button;
	

	public AbstractWindow(String title, int upperLeftCornerX, int upperLeftCornerY) {
		super(title);

		setLocation(new Point(upperLeftCornerX, upperLeftCornerY));
		setPreferredSize(new Dimension(Util.DEFAULT_WIDTH, Util.DEFAULT_HEIGHT));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		this.getContentPane().add(textArea, BorderLayout.NORTH);

		button = new JButton();
		button.setText(title);
		this.getContentPane().add(button, BorderLayout.SOUTH);

	}
	
}
