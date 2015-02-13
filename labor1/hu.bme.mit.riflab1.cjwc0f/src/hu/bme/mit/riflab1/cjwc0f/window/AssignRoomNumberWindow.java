package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.AssignRoomNumberWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AssignRoomNumberWindow extends AbstractWindow {

	public AssignRoomNumberWindow(AssignRoomNumberWorker assignRoomNumberWorker) {
		super("Assign room number", 1300, 50, 100, 100);

		assignRoomNumberWorker.addTextArea(textArea);
		assignRoomNumberWorker.execute();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				assignRoomNumberWorker.clicked();
			}

		});

	}

}
