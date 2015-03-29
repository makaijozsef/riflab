package hu.bme.mit.cjwc0f.labor4.addcommpoints;

import hu.bme.mit.cjwc0f.labor4.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor4.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor4.workflow.AddCommunityPoints;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class AddCommunityPointsWindow extends AbstractWindow {

	private final class SwingWorkerExtension extends SwingWorker<Void, Void> {

		private Boolean b;

		public SwingWorkerExtension(Boolean b) {
			this.b = b;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			button.setEnabled(false);
			buttonTrue.setEnabled(false);
			buttonFalse.setEnabled(false);
			if(receivedMessage == null){
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}
			while (receivedMessage == null) {
				Thread.sleep(200);
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}
			
			
			ApplicationData applicantData = (ApplicationData) receivedMessage.getObject();
			ApplicationData resultData = AddCommunityPoints.calculate(applicantData);
			
			ObjectMessage message = session.createObjectMessage(resultData);
			if(b == null){				
				if(resultData.getAverage() >= 3){
					senderRoom.send(message);
				} else {
					senderFinal.send(message);
				}
			} else if (b == false){
				senderFinal.send(message);
			} else if (b == true){
				senderRoom.send(message);
			}
			textArea.setText(resultData.toString());
			
			receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			while (receivedMessage == null) {
				Thread.sleep(200);
				receivedMessage = (ObjectMessage) receiver.receiveNoWait();
			}

			button.setEnabled(true);
			buttonTrue.setEnabled(true);
			buttonFalse.setEnabled(true);
			return null;
			
		}
	}
	
//	private Queue inputQueue;
	private MessageConsumer receiver;
//	private Queue outputRoomQueue;
	private MessageProducer senderRoom;
//	private Queue outputFinalQueue;
	private MessageProducer senderFinal;
	

	private boolean firstClickStudy;

	private ObjectMessage receivedMessage;
	private JButton buttonTrue;
	private JButton buttonFalse;

	public AddCommunityPointsWindow() {
		super("Add community points", 700, 100);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Add community points - " + jvmName);
		
		createQueueSession();
		try {
			Topic inputQueue = (Topic) initialContext.lookup("topic/determineAverage");
			receiver = session.createConsumer(inputQueue);
			Topic outputRoomQueue = (Topic) initialContext.lookup("topic/roomAssignment");
			senderRoom = session.createProducer(outputRoomQueue);
			Topic outputFinalQueue = (Topic) initialContext.lookup("topic/finalResult");
			senderFinal = session.createProducer(outputFinalQueue);
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
				
				SwingWorker<Void, Void> messageReader = new SwingWorkerExtension(null);
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


		buttonTrue = new JButton();
		buttonTrue.setText("Go to room assignment");
		buttonTrue.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, Void> messageReader = new SwingWorkerExtension(true);
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
		this.getContentPane().add(buttonTrue, BorderLayout.EAST);

		buttonFalse = new JButton();
		buttonFalse.setText("Skip room assignment");
		buttonFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingWorker<Void, Void> messageReader = new SwingWorkerExtension(false);
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
		this.getContentPane().add(buttonFalse, BorderLayout.WEST);

	}
	


}
