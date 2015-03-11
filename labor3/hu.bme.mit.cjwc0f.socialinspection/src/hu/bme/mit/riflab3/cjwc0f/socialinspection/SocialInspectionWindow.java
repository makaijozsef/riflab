package hu.bme.mit.riflab3.cjwc0f.socialinspection;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.SocialInspection;
import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

@SuppressWarnings("serial")
public class SocialInspectionWindow extends AbstractWindow {

	public SocialInspectionWindow() {
		super("Social inspection", 700, 500);
		
		try {
			//TODO obtain host from the command line arguments
			createQueue("localhost", IQueueNames.SOCIAL_INSPECTION, IQueueNames.FINAL_RESULT_SI);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    try {
	    	// TODO change second parameter to false to use the autoAck
			channel.basicConsume(IQueueNames.SOCIAL_INSPECTION, true, consumer);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create consumer");
		}


		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					SocialResult socialResult = SocialInspection.createResult(applicationData);
					textArea.setText(socialResult.toString());
					
					byte[] payload = Util.serialize(socialResult);
					channel.basicPublish("", IQueueNames.FINAL_RESULT_SI, null, payload);
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}

			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}

}
