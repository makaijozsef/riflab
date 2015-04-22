package hu.bme.mit.cjwc0f.labor5.assignroomnumber;

import hu.bme.mit.cjwc0f.events.WaitsForJoin;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.drools.EventQueue;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.AssignRoomNumber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Queue;

@SuppressWarnings("serial")
public class AssignRoomNumberWindow extends AbstractWindow {

	public AssignRoomNumberWindow(final Queue<Serializable> inQueue, final Queue<ApplicationData> outQueue) {
		super("Assign room number", 1050, 0);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Assign room number - " + jvmName);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueue.poll();
				ApplicationData roomAssigned = AssignRoomNumber.assignRoom(applicationData);

				outQueue.add(roomAssigned);
				EventQueue.add(new WaitsForJoin(roomAssigned.getTimestamp()));

				textArea.setText(roomAssigned.toString());
			}

		});

		Thread t1 = new Thread(new QueueObserver(button, inQueue));
		t1.setDaemon(true);
		t1.start();

	}

}
