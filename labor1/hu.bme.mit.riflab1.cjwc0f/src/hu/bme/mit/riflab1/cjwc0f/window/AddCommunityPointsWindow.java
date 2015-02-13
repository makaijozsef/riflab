package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.AddCommunityPointsWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class AddCommunityPointsWindow extends AbstractWindow {

	public AddCommunityPointsWindow(AddCommunityPointsWorker addCommunityPointsWorker) {
		super("Add community points", 1000, 100, 100, 100);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCommunityPointsWorker.clicked();
			}

		});

		addCommunityPointsWorker.addTextArea(textArea);
		addCommunityPointsWorker.execute();

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
