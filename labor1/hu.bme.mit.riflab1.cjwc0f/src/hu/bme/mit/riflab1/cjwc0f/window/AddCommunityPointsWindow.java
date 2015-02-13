package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.AddCommunityPointsWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AddCommunityPointsWindow extends AbstractWindow {

	private static final long serialVersionUID = 3714604390937043362L;

	public AddCommunityPointsWindow(AddCommunityPointsWorker addCommunityPointsWorker) {
		super("Add community points");

		JLabel label = new JLabel();
		this.getContentPane().add(label, BorderLayout.NORTH);
		addCommunityPointsWorker.addLabel(label);

		addCommunityPointsWorker.execute();

		JButton button = new JButton();
		button.setText("Add points");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCommunityPointsWorker.clicked();
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);

		JButton buttonTrue = new JButton();
		buttonTrue.setText("Go to room assignment");
		buttonTrue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCommunityPointsWorker.clickedTrue();
			}

		});
		this.getContentPane().add(buttonTrue, BorderLayout.EAST);

		JButton buttonFalse = new JButton();
		buttonFalse.setText("Skip room assignment");
		buttonFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCommunityPointsWorker.clickedFalse();
			}

		});
		this.getContentPane().add(buttonFalse, BorderLayout.WEST);

		
	}

}
