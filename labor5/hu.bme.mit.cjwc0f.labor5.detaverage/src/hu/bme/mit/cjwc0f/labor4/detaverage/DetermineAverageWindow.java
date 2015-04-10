package hu.bme.mit.cjwc0f.labor4.detaverage;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.DetermineAverage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Queue;

@SuppressWarnings("serial")
public class DetermineAverageWindow extends AbstractWindow {

	public DetermineAverageWindow(Queue<Serializable> inQueue, Queue<ApplicationData> outQueue) {
		super("Determine average", 350, 100);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine average - " + jvmName);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueue.poll();
				ApplicationData calculatedData = DetermineAverage.calculate(applicationData);

				outQueue.add(calculatedData);

				textArea.setText(calculatedData.toString());
			}

		});

		Thread t1 = new Thread(new QueueObserver(button, inQueue));
		t1.setDaemon(true);
		t1.start();
	}

}
