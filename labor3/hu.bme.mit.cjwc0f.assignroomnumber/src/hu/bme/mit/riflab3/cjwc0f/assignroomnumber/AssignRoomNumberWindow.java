package hu.bme.mit.riflab3.cjwc0f.assignroomnumber;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.AddCommunityPoints;
import hu.bme.mit.riflab3.cjwc0f.workflow.AssignRoomNumber;
import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

@SuppressWarnings("serial")
public class AssignRoomNumberWindow extends AbstractWindow {

	public AssignRoomNumberWindow() {
		super("Assign room number", 1050, 0);


		
		try {
			//TODO obtain host from the command line arguments
			createQueue("localhost", IQueueNames.COMMUNITY_POINTS_ROOM, IQueueNames.FINAL_RESULT_AD);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    try {
			channel.basicConsume(IQueueNames.COMMUNITY_POINTS_ROOM, true, consumer);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create consumer");
		}


		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					QueueingConsumer.Delivery delivery = consumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					ApplicationData resultApplicantData = AssignRoomNumber.assignRoom(applicationData);
					int roomNumber = resultApplicantData.getResult().getRoomNumber();
					resultApplicantData.setRoomNumber(roomNumber);
					resultApplicantData.setAdmitted(roomNumber > 0);
					textArea.setText(resultApplicantData.toString());
					
					byte[] payload = Util.serialize(resultApplicantData);
					channel.basicPublish("", IQueueNames.FINAL_RESULT_AD, null, payload);
					
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}

			}

		});
		

	}

}
