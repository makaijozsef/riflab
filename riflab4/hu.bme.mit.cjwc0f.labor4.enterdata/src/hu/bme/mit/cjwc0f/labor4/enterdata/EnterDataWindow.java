package hu.bme.mit.cjwc0f.labor4.enterdata;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.EnterApplicantData;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.NamingException;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class EnterDataWindow extends AbstractWindow {
	
	private Topic topic;
	private MessageProducer producer;
	private Topic topicAverage;
	private MessageProducer producerAverage;
	private Topic topicSocial;
	private MessageProducer producerSocial;

	public EnterDataWindow(){
		super("Enter applicant data", 0, 300);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Enter applicant data - " + jvmName);
		
		JButton button = new JButton();
		button.setText("GenerateData");
		this.getContentPane().add(button, BorderLayout.SOUTH);
		
		//*
		queueSolution(button);
		/*/
		topicSolution(button);
		//*/
	}

	private void queueSolution(JButton button) {
		createQueueSession();
		try {
			topicAverage = (Topic) initialContext.lookup("topic/enterDataAverage");
			producerAverage = session.createProducer(topicAverage);
			topicSocial = (Topic) initialContext.lookup("topic/enterDataSocial");
			producerSocial = session.createProducer(topicSocial);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = EnterApplicantData.generate();
				
				try {
		        	ObjectMessage message = session.createObjectMessage(applicationData);
					producerAverage.send(message);
					producerSocial.send(message);
					textArea.setText(applicationData.toString());
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}
			
		});
	}

	private void topicSolution(JButton button) {
		createTopicSession();
		try {
			topic = session.createTopic("enterdata");
			producer = session.createProducer(topic);
		} catch (JMSException e1) {
			Logger.global.log(Level.SEVERE, "Error while creating topic: " + e1.getMessage());
		}

		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = EnterApplicantData.generate();
				
				try {
		        	ObjectMessage message = session.createObjectMessage(applicationData);
					producer.send(message);
					textArea.setText(applicationData.toString());
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}
			
		});
	}
	
	

}
