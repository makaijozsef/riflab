package hu.bme.mit.cjwc0f.labor5.socialinspection;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.SocialInspection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Queue;

@SuppressWarnings("serial")
public class SocialInspectionWindow extends AbstractWindow {

	public SocialInspectionWindow(final Queue<Serializable> inQueue, final Queue<SocialResult> outQueue) {
		super("Social inspection", 700, 500);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Social inspection - " + jvmName);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueue.poll();
				SocialResult socialResult = SocialInspection.createResult(applicationData);

				outQueue.add(socialResult);

				textArea.setText(socialResult.toString());
			}
		});

		Thread t1 = new Thread(new QueueObserver(button, inQueue));
		t1.setDaemon(true);
		t1.start();

	}

}
