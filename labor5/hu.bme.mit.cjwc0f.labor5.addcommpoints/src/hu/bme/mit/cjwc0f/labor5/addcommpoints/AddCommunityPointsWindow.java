package hu.bme.mit.cjwc0f.labor5.addcommpoints;

import hu.bme.mit.cjwc0f.events.AssignRoomChosen;
import hu.bme.mit.cjwc0f.events.DetFinalResultChosen;
import hu.bme.mit.cjwc0f.events.StudyWaitsForJoin;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.drools.EventQueue;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.AddCommunityPoints;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Queue;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class AddCommunityPointsWindow extends AbstractWindow {

	private JButton buttonTrue;
	private JButton buttonFalse;

	public AddCommunityPointsWindow(final Queue<Serializable> inQueue, final Queue<ApplicationData> outQueueAssign,
			final Queue<ApplicationData> outQueueFinal) {
		super("Add community points", 700, 100);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Add community points - " + jvmName);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueue.poll();
				ApplicationData calculatedData = AddCommunityPoints.calculate(applicationData);

				if (calculatedData.getAverage() >= 3) {
					outQueueAssign.add(calculatedData);
				} else {
					outQueueFinal.add(calculatedData);
					EventQueue.add(new StudyWaitsForJoin(calculatedData.getTimestamp()));
					EventQueue.add(new DetFinalResultChosen());
				}

				textArea.setText(calculatedData.toString());
			}

		});

		buttonTrue = new JButton();
		buttonTrue.setText("Go to room assignment");
		buttonTrue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueue.poll();
				outQueueAssign.add(applicationData);
				EventQueue.add(new AssignRoomChosen());
				textArea.setText(applicationData.toString());
			}

		});
		this.getContentPane().add(buttonTrue, BorderLayout.EAST);

		buttonFalse = new JButton();
		buttonFalse.setText("Skip room assignment");
		buttonFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueue.poll();
				outQueueFinal.add(applicationData);
				EventQueue.add(new StudyWaitsForJoin(applicationData.getTimestamp()));
				EventQueue.add(new DetFinalResultChosen());
				textArea.setText(applicationData.toString());
			}

		});
		this.getContentPane().add(buttonFalse, BorderLayout.WEST);

		Thread t1 = new Thread(new QueueObserver(button, inQueue));
		t1.setDaemon(true);
		t1.start();
		Thread t2 = new Thread(new QueueObserver(buttonFalse, inQueue));
		t2.setDaemon(true);
		t2.start();
		Thread t3 = new Thread(new QueueObserver(buttonTrue, inQueue));
		t3.setDaemon(true);
		t3.start();

	}

}
