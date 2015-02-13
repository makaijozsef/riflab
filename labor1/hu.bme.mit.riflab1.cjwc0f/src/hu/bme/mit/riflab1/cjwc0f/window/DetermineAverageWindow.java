package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.DetermineAverageWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class DetermineAverageWindow extends AbstractWindow {

	private static final long serialVersionUID = 3714604390937043362L;

	public DetermineAverageWindow(DetermineAverageWorker determineAverageWorker) {
		super("Determine average");

		JLabel label = new JLabel();
		this.getContentPane().add(label, BorderLayout.NORTH);
		determineAverageWorker.addLabel(label);

		determineAverageWorker.execute();

		JButton button = new JButton();
		button.setText("Determine average");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				determineAverageWorker.clicked();
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}

}
