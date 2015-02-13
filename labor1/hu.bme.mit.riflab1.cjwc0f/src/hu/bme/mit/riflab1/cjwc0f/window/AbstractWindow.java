package hu.bme.mit.riflab1.cjwc0f.window;

import javax.swing.JFrame;

public class AbstractWindow extends JFrame {

	private static final long serialVersionUID = 4328057907979649641L;

	public AbstractWindow(String title) {
		super(title);		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
