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
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.naming.NamingException;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class EnterDataWindow extends AbstractWindow {
	
	private Topic topic;
	private MessageProducer producer;
	private Queue queueAverage;
	private QueueSender senderAverage;
	private Queue queueSocial;
	private QueueSender senderSocial;

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
			queueAverage = (Queue) initialContext.lookup("queue/enterDataQueueAverage");
			senderAverage = ((QueueSession)session).createSender(queueAverage);
			queueSocial = (Queue) initialContext.lookup("queue/enterDataQueueSocial");
			senderSocial = ((QueueSession)session).createSender(queueSocial);
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
					senderAverage.send(message);
					senderSocial.send(message);
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
