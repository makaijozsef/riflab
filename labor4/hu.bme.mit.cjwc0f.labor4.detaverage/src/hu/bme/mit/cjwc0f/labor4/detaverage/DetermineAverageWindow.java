package hu.bme.mit.cjwc0f.labor4.detaverage;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.DetermineAverage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.NamingException;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class DetermineAverageWindow extends AbstractWindow {

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
			ApplicationData resultData = DetermineAverage.calculate(applicantData);
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
	
	private Queue inputQueue;
	private QueueReceiver receiver;
	private Queue outputQueue;
	private QueueSender sender;

	private boolean firstClick = true;

	
	private ObjectMessage receivedMessage;

	public DetermineAverageWindow() {
		super("Determine average", 350, 100);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine average - " + jvmName);

		
		createQueueSession();
		try {
			inputQueue = (Queue) initialContext.lookup("queue/enterDataQueueAverage");
			receiver = ((QueueSession)session).createReceiver(inputQueue);
			outputQueue = (Queue) initialContext.lookup("queue/determineAverageQueue");
			sender = ((QueueSession)session).createSender(outputQueue);
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
				if (firstClick ) {
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
