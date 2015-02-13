package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.AddCommunityPointsWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.AssignRoomNumberWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AssignRoomNumberWindow extends AbstractWindow {

	private static final long serialVersionUID = 3714604390937043362L;

	public AssignRoomNumberWindow(AssignRoomNumberWorker assignRoomNumberWorker) {
		super("Assign room number");

		JLabel label = new JLabel();
		this.getContentPane().add(label, BorderLayout.NORTH);
		assignRoomNumberWorker.addLabel(label);

		assignRoomNumberWorker.execute();

		JButton button = new JButton();
		button.setText("Assign room");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				assignRoomNumberWorker.clicked();
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
		
	}

}
