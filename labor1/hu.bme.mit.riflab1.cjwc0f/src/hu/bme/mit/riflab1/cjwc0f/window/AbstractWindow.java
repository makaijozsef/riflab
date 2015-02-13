package hu.bme.mit.riflab1.cjwc0f.window;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public abstract class AbstractWindow extends JFrame {

	protected JTextArea textArea;
	protected JButton button;

	public AbstractWindow(String title, int upperLeftCornerX, int upperLeftCornerY, int width, int height) {
		super(title);

		// setLocation(new Point(upperLeftCornerX, upperLeftCornerY));
		setBounds(upperLeftCornerX, upperLeftCornerY, upperLeftCornerX + width, upperLeftCornerY + height);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		this.getContentPane().add(textArea, BorderLayout.NORTH);

		button = new JButton();
		button.setText(title);
		this.getContentPane().add(button, BorderLayout.SOUTH);

	}

}
