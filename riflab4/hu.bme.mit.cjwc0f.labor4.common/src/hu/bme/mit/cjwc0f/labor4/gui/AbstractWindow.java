package hu.bme.mit.cjwc0f.labor4.gui;

import hu.bme.mit.cjwc0f.labor4.workflow.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public abstract class AbstractWindow extends JFrame {

	protected JTextArea textArea;
	protected JButton button;
	
	protected Connection conn;
	protected Session session;
	protected InitialContext initialContext;

	public AbstractWindow(String title, int upperLeftCornerX, int upperLeftCornerY) {
		super(title);

		setLocation(new Point(upperLeftCornerX, upperLeftCornerY));
		setPreferredSize(new Dimension(Util.DEFAULT_WIDTH, Util.DEFAULT_HEIGHT));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		this.getContentPane().add(textArea, BorderLayout.NORTH);

		button = new JButton();
		button.setText(title);
		this.getContentPane().add(button, BorderLayout.SOUTH);

	}
	
	protected void createQueueSession(){
		try {
			initialContext = new InitialContext();
			QueueConnectionFactory factory = (QueueConnectionFactory) initialContext
					.lookup("QueueConnectionFactory");
			conn = factory.createQueueConnection();
			conn.start();
			session = ((QueueConnection)conn).createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			Logger.global.log(Level.SEVERE, "Session could not be created: " + e.getMessage());
			System.exit(ERROR);
		} catch (NamingException e) {
			Logger.global.log(Level.SEVERE, "Could not find JNDI server, check project settings please!");
			System.exit(ERROR);
		}
	}
	
	protected void createTopicSession(){
		
		try {
			InitialContext initialContext = new InitialContext();
			TopicConnectionFactory factory = (TopicConnectionFactory) initialContext
					.lookup("TopicConnectionFactory");
			conn = factory.createConnection();
			conn.start();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			Logger.global.log(Level.SEVERE, "Session could not be created: " + e.getMessage());
			System.exit(ERROR);
		} catch (NamingException e) {
			Logger.global.log(Level.SEVERE, "Could not find JNDI server, check project settings please!");
			System.exit(ERROR);
		}
	}
	
	protected void disposeConnection(){
		try {
			if (session != null) {
				session.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (JMSException e) {
			Logger.global.log(Level.SEVERE, "Connection was not closed properly.");
		}
	}
	
}
