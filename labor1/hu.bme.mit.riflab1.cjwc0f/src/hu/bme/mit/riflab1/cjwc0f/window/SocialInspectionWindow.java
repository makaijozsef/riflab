package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.SocialInspectionWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class SocialInspectionWindow extends AbstractWindow {

	private static final long serialVersionUID = 3714604390937043362L;

	public SocialInspectionWindow(SocialInspectionWorker socialInspectionWorker) {
		super("Social inspection", 500, 300, 100, 100);

		JLabel label = new JLabel();
		this.getContentPane().add(label, BorderLayout.NORTH);
		socialInspectionWorker.addLabel(label);

		socialInspectionWorker.execute();

		JButton button = new JButton();
		button.setText("Inspect");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				socialInspectionWorker.clicked();
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}

}
