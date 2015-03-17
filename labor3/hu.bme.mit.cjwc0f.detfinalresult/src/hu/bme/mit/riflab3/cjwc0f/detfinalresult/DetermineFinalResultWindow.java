package hu.bme.mit.riflab3.cjwc0f.detfinalresult;

import hu.bme.mit.riflab3.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab3.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab3.cjwc0f.gui.AbstractWindow;
import hu.bme.mit.riflab3.cjwc0f.queues.IQueueNames;
import hu.bme.mit.riflab3.cjwc0f.workflow.DetermineFinalResult;
import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

@SuppressWarnings("serial")
public class DetermineFinalResultWindow extends AbstractWindow {
	
	private List<ApplicationData> applicationDatas = new ArrayList<ApplicationData>();
	private List<SocialResult> socialResults = new ArrayList<SocialResult>();
	
	private Map<ApplicationData, QueueingConsumer.Delivery> applicationsToDeliveries = new HashMap<ApplicationData, QueueingConsumer.Delivery>();
	private Map<SocialResult, QueueingConsumer.Delivery> socialsToDeliveries = new HashMap<SocialResult, QueueingConsumer.Delivery>();

	public DetermineFinalResultWindow(String mqHost) {
		
		super("Determine final result", 1400, 300);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine final result - " + jvmName);

		try {
			//TODO obtain host from the command line arguments
			createQueue(mqHost, IQueueNames.FINAL_RESULT_SI, IQueueNames.FINAL_RESULT_AD);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create channel");
			System.exit(ERROR);
		}
		
		final QueueingConsumer appDataConsumer = new QueueingConsumer(channel);
		final QueueingConsumer socialDataConsumer = new QueueingConsumer(channel);
	    try {
			channel.basicConsume(IQueueNames.FINAL_RESULT_AD, false, appDataConsumer);
			channel.basicConsume(IQueueNames.FINAL_RESULT_SI, false, socialDataConsumer);
		} catch (IOException e1) {
			Logger.getGlobal().log(Level.SEVERE, "Could not create consumer");
		}


	    button.setText("Consume from study part");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					QueueingConsumer.Delivery delivery = appDataConsumer.nextDelivery();
					ApplicationData applicationData = (ApplicationData)Util.deserialize(delivery.getBody());
					
					SocialResult matchingSR = null;
					for (SocialResult sr : socialResults) {
						if(applicationData.getTimestamp().equals(sr.getApplicantData().getTimestamp())){
							matchingSR = sr;
							break;
						}
					}
					if(matchingSR != null){
						Delivery removedDelivery = socialsToDeliveries.remove(matchingSR);
						socialResults.remove(matchingSR);
						
						ApplicationData finalResult = DetermineFinalResult.decide(applicationData, matchingSR);
						
						channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
						channel.basicAck(removedDelivery.getEnvelope().getDeliveryTag(), false);
						
						textArea.setText(finalResult.toString());
					}
					else {
						applicationDatas.add(applicationData);
						applicationsToDeliveries.put(applicationData, delivery);
					}
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message from study part");
				}

			}

		});
		
		this.getContentPane().add(button, BorderLayout.WEST);
		
		JButton socialButton = new JButton();
		socialButton.setText("Consume from social part");
		
		socialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					QueueingConsumer.Delivery deliverySI = socialDataConsumer.nextDelivery();
					SocialResult socialResult = (SocialResult)Util.deserialize(deliverySI.getBody());
					
					ApplicationData matchingAD = null;
					for (ApplicationData ad : applicationDatas) {
						if(ad.getTimestamp().equals(socialResult.getApplicantData().getTimestamp())){
							matchingAD = ad;
							break;
						}
					}
					if(matchingAD != null){
						Delivery removedDelivery = applicationsToDeliveries.remove(matchingAD);
						applicationDatas.remove(matchingAD);
						
						ApplicationData finalResult = DetermineFinalResult.decide(matchingAD, socialResult);
						
						channel.basicAck(deliverySI.getEnvelope().getDeliveryTag(), false);
						channel.basicAck(removedDelivery.getEnvelope().getDeliveryTag(), false);
						
						textArea.setText(finalResult.toString());
					}
					else {
						socialResults.add(socialResult);
						socialsToDeliveries.put(socialResult, deliverySI);
					}
					
					
				} catch (ShutdownSignalException | ConsumerCancelledException
						| InterruptedException | ClassNotFoundException | IOException e1) {
					Logger.getGlobal().log(Level.SEVERE, "Could not receive message from social part");
				}

			}

		});
		
		this.getContentPane().add(socialButton, BorderLayout.EAST);

	}

}
