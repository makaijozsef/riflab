package hu.bme.mit.riflab3.cjwc0f.addcommpoints;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.AddCommunityPoints;
import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

@SuppressWarnings("serial")
public class AddCommunityPointsWindow extends AbstractWindow {

	public AddCommunityPointsWindow() {
		super("Add community points", 700, 100);

		
		try {
			//TODO obtain host from the command line arguments
			createQueue("localhost", IQueueNames.COMMUNITY_POINTS, IQueueNames.COMMUNITY_POINTS_ROOM, IQueueNames.FINAL_RESULT_AD);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    try {
			channel.basicConsume(IQueueNames.COMMUNITY_POINTS, false, consumer);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create consumer");
		}


		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					
					ApplicationData resultApplicantData = AddCommunityPoints.calculate(applicationData);
					textArea.setText(resultApplicantData.toString());
					
					byte[] payload = Util.serialize(resultApplicantData);
					if(resultApplicantData.getAverage() >= 3){
						channel.basicPublish("", IQueueNames.COMMUNITY_POINTS_ROOM, null, payload);
					} else {
						channel.basicPublish("", IQueueNames.FINAL_RESULT_AD, null, payload);
					}
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}

			}

		});
		


		JButton buttonTrue = new JButton();
		buttonTrue.setText("Go to room assignment");
		buttonTrue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					ApplicationData resultApplicantData = AddCommunityPoints.calculate(applicationData);
					textArea.setText(resultApplicantData.toString());
					
					byte[] payload = Util.serialize(resultApplicantData);
					channel.basicPublish("", IQueueNames.COMMUNITY_POINTS_ROOM, null, payload);
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}
			}

		});
		this.getContentPane().add(buttonTrue, BorderLayout.EAST);

		JButton buttonFalse = new JButton();
		buttonFalse.setText("Skip room assignment");
		buttonFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					ApplicationData resultApplicantData = AddCommunityPoints.calculate(applicationData);
					textArea.setText(resultApplicantData.toString());
					
					byte[] payload = Util.serialize(resultApplicantData);
					channel.basicPublish("", IQueueNames.FINAL_RESULT_AD, null, payload);
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}
			}

		});
		this.getContentPane().add(buttonFalse, BorderLayout.WEST);

	}

}
