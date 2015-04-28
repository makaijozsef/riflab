package hu.bme.mit.cjwc0f.labor5.gui;

import hu.bme.mit.cjwc0f.labor5.workflow.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public abstract class AbstractWindow extends JFrame {

	protected JTextArea textArea;
	protected JButton button;

	protected String taskName;

	protected class QueueObserver implements Runnable {

		private JButton internalButton;
		private Queue<?> observedQueue;

		public QueueObserver(JButton button, Queue<?> observedQueue) {
			internalButton = button;
			this.observedQueue = observedQueue;
		}

		@Override
		public void run() {
			while (true) {
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							internalButton.setEnabled(!observedQueue.isEmpty());
						}
					});
					Thread.sleep(250);
				} catch (InterruptedException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Sleep interrupted");
				} catch (InvocationTargetException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Button enable/disable failed");
				}
			}
		}
	}

	public AbstractWindow(String title, int upperLeftCornerX, int upperLeftCornerY) {
		super(title);
		taskName = title;

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
