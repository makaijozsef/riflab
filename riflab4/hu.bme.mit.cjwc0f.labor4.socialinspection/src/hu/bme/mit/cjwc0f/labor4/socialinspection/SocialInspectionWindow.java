package hu.bme.mit.cjwc0f.labor4.socialinspection;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.data.SocialResult;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.SocialInspection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.NamingException;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class SocialInspectionWindow extends AbstractWindow {
	
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
			SocialResult socialResult = SocialInspection.createResult(applicantData);
			ObjectMessage message = session.createObjectMessage(socialResult);
			sender.send(message);
			textArea.setText(socialResult.toString());
			
			receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			while (receivedMessage == null) {
				Thread.sleep(200);
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}
			button.setEnabled(true);
			return null;
		}

	}

	private Topic inputQueue;
	private MessageProducer sender;
	private Topic outputQueue;
	private MessageConsumer receiver;
	private ObjectMessage receivedMessage;
	private boolean firstClick = true;
	
	public SocialInspectionWindow() {
		super("Social inspection", 700, 500);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Social inspection - " + jvmName);
		
		createQueueSession();
		try {
			inputQueue = (Topic) initialContext.lookup("topic/enterDataSocial");
			receiver = session.createConsumer(inputQueue);
			outputQueue = (Topic) initialContext.lookup("topic/socialInspection");
			sender = session.createProducer(outputQueue);
		} catch (NamingException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, "Problem occured with the JNDI: " + e.getMessage());
		} catch (JMSException e1) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, "Error with creating receiver/sender: " + e1.getMessage());
		}

		receivedMessage = null;
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> messageReader = new SwingWorkerExtension();
				if (firstClick) {
					// Only for init
					firstClick = false;
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
