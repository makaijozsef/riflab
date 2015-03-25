package hu.bme.mit.cjwc0f.labor4.addcommpoints;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.AddCommunityPoints;
import hu.bme.mit.cjwc0f.labor4.workflow.DetermineAverage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.NamingException;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class AddCommunityPointsWindow extends AbstractWindow {

	private Queue inputQueue;
	private QueueReceiver receiver;
	private Queue outputRoomQueue;
	private QueueSender senderRoom;
	private Queue outputFinalQueue;
	private QueueSender senderFinal;

	public AddCommunityPointsWindow() {
		super("Add community points", 700, 100);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Add community points - " + jvmName);
		
		createQueueSession();
		try {
			inputQueue = (Queue) initialContext.lookup("queue/determineAverageQueue");
			receiver = ((QueueSession)session).createReceiver(inputQueue);
			outputRoomQueue = (Queue) initialContext.lookup("queue/roomAssignmentQueue");
			senderRoom = ((QueueSession)session).createSender(outputRoomQueue);
			outputFinalQueue = (Queue) initialContext.lookup("queue/finalResultQueue");
			senderFinal = ((QueueSession)session).createSender(outputFinalQueue);
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
				
				try {
					ObjectMessage receive = ((ObjectMessage)receiver.receive());
					ApplicationData applicantData = (ApplicationData) receive.getObject();
					ApplicationData resultData = AddCommunityPoints.calculate(applicantData);
					
					ObjectMessage message = session.createObjectMessage(resultData);
					if(resultData.getAverage() >= 3){
						senderRoom.send(message);
					} else {
						senderFinal.send(message);
					}
					textArea.setText(resultData.toString());
					
				} catch (JMSException e1) {
					Logger.global.log(Level.SEVERE, "Could not publish message: " + e1.getMessage());
				}
			}
			
		});


		JButton buttonTrue = new JButton();
		buttonTrue.setText("Go to room assignment");
		buttonTrue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					ObjectMessage receive = ((ObjectMessage)receiver.receive());
					ApplicationData applicantData = (ApplicationData) receive.getObject();
					ApplicationData resultData = AddCommunityPoints.calculate(applicantData);
					
					ObjectMessage message = session.createObjectMessage(resultData);
					senderRoom.send(message);
					textArea.setText(resultData.toString());
				
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		this.getContentPane().add(buttonTrue, BorderLayout.EAST);

		JButton buttonFalse = new JButton();
		buttonFalse.setText("Skip room assignment");
		buttonFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectMessage receive = ((ObjectMessage)receiver.receive());
					ApplicationData applicantData = (ApplicationData) receive.getObject();
					ApplicationData resultData = AddCommunityPoints.calculate(applicantData);
					
					ObjectMessage message = session.createObjectMessage(resultData);
					senderFinal.send(message);
					textArea.setText(resultData.toString());
					
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		this.getContentPane().add(buttonFalse, BorderLayout.WEST);

	}

}
