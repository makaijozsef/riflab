package hu.bme.mit.riflab3.cjwc0f.detfinalresult;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.DetermineFinalResult;
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
public class DetermineFinalResultWindow extends AbstractWindow {

	public DetermineFinalResultWindow() {
		super("Determine final result", 1400, 300);


		try {
			//TODO obtain host from the command line arguments
			createQueue("localhost", IQueueNames.FINAL_RESULT_SI, IQueueNames.FINAL_RESULT_AD);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}
		
		QueueingConsumer appDataConsumer = new QueueingConsumer(channel);
		QueueingConsumer socialDataConsumer = new QueueingConsumer(channel);
	    try {
			channel.basicConsume(IQueueNames.FINAL_RESULT_AD, false, appDataConsumer);
			channel.basicConsume(IQueueNames.FINAL_RESULT_SI, false, socialDataConsumer);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create consumer");
		}


		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					QueueingConsumer.Delivery delivery = appDataConsumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					QueueingConsumer.Delivery deliverySI = socialDataConsumer.nextDelivery();
					SocialResult socialResult = (SocialResult)Util.deserialize(deliverySI.getBody());
					
					ApplicationData finalResult = DetermineFinalResult.decide(applicationData, socialResult);
					
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					channel.basicAck(deliverySI.getEnvelope().getDeliveryTag(), false);
					
					textArea.setText(finalResult.toString());
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}

			}

		});
		

	}

}
