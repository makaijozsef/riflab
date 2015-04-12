package hu.bme.mit.cjwc0f.labor5.enterdata;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.EnterApplicantData;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.Queue;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class EnterDataWindow extends AbstractWindow {

	public EnterDataWindow(final Queue<ApplicationData> socialQueue, final Queue<ApplicationData> detaverageQueue) {
		super("Enter applicant data", 0, 300);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Enter applicant data - " + jvmName);

		JButton button = new JButton();
		button.setText("GenerateData");
		this.getContentPane().add(button, BorderLayout.SOUTH);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = EnterApplicantData.generate();
				
				socialQueue.add(applicationData);
				detaverageQueue.add(applicationData);
				
				textArea.setText(applicationData.toString());

			}

		});

	}

}
