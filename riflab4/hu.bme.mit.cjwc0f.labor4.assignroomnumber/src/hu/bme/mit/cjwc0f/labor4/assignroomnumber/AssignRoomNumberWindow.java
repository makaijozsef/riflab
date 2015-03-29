package hu.bme.mit.cjwc0f.labor4.assignroomnumber;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.AssignRoomNumber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.naming.NamingException;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class AssignRoomNumberWindow extends AbstractWindow {
	private final class SwingWorkerExtension extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			button.setEnabled(false);
			if(receivedMessage == null){
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}
			while (receivedMessage == null) {
				Thread.sleep(200);
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}
			
			
			ApplicationData applicantData = (ApplicationData) receivedMessage.getObject();
			ApplicationData resultData = AssignRoomNumber.assignRoom(applicantData);
        	ObjectMessage message = session.createObjectMessage(resultData);
			sender.send(message);
			textArea.setText(resultData.toString());

			
			receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			while (receivedMessage == null) {
				Thread.sleep(200);
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}
			button.setEnabled(true);
			return null;
			
		}
	}
	
	
	private MessageConsumer receiver;
	private MessageProducer sender;
	

	private ObjectMessage receivedMessage;
	
	private boolean firstClickStudy = true;


	public AssignRoomNumberWindow() {
		super("Assign room number", 1050, 0);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Assign room number - " + jvmName);
		
		createQueueSession();
		try {
			Topic inputTopic = (Topic)initialContext.lookup("topic/roomAssignment");
			receiver = session.createConsumer(inputTopic);
			Topic outputTopic = (Topic) initialContext.lookup("topic/finalResult");
			sender = ((QueueSession)session).createProducer(outputTopic);
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
				
				SwingWorker<Void, Void> messageReader = new SwingWorkerExtension();
				if (firstClickStudy) {
					// Only for init
					firstClickStudy = false;
					messageReader.execute();
					if(receivedMessage == null){
						textArea.setText("The first input is not ready yet.");
					}
				} else {
					messageReader.execute();
				}
				
			}
		
		});

	}

}
