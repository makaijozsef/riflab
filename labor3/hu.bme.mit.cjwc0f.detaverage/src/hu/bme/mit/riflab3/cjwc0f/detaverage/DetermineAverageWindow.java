package hu.bme.mit.riflab3.cjwc0f.detaverage;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.DetermineAverage;
import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

@SuppressWarnings("serial")
public class DetermineAverageWindow extends AbstractWindow {

	public DetermineAverageWindow() {
		super("Determine average", 350, 100);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine average - " + jvmName);

		try {
			//TODO obtain host from the command line arguments
			createQueue("localhost", IQueueNames.DETERMINE_AVERAGE, IQueueNames.COMMUNITY_POINTS);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
	    try {
			channel.basicConsume(IQueueNames.DETERMINE_AVERAGE, false, consumer);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create consumer");
		}

		JButton button = new JButton();
		button.setText("Determine average");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				QueueingConsumer.Delivery delivery;
				ApplicationData applicationData = null;
				ApplicationData resultAppData = null;
				try {
					delivery = consumer.nextDelivery();
					applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					resultAppData = DetermineAverage.calculate(applicationData);
					
					byte[] payload = Util.serialize(resultAppData);
					channel.basicPublish("", IQueueNames.COMMUNITY_POINTS, null, payload);
					
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					
					textArea.setText(resultAppData.toString());
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e2) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message");
				}
				
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
		
	}

}
