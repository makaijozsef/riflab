package hu.bme.mit.riflab3.cjwc0f.enterdata;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.EnterApplicantData;
import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class EnterDataWindow extends AbstractWindow {

	public EnterDataWindow(String mqHost){
		super("Enter applicant data", 0, 300);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Enter applicant data - " + jvmName);
		
		try {
			//TODO obtain host from the command line arguments
			createQueue(mqHost, IQueueNames.DETERMINE_AVERAGE);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}

		JButton button = new JButton();
		button.setText("GenerateData");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = EnterApplicantData.generate();
			    try {
			    	byte[] payload = Util.serialize(applicationData);
					channel.basicPublish("", IQueueNames.DETERMINE_AVERAGE, null, payload);
					channel.basicPublish("", IQueueNames.SOCIAL_INSPECTION, null, payload);
					textArea.setText(applicationData.toString());
				} catch (IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not publish message: " + applicationData.toString());
				}
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}
	
	

}
