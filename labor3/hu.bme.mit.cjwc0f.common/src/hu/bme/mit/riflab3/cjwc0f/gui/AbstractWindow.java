package hu.bme.mit.riflab3.cjwc0f.gui;

import hu.bme.mit.riflab3.cjwc0f.workflow.Util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@SuppressWarnings("serial")
public abstract class AbstractWindow extends JFrame {

	protected JTextArea textArea;
	protected JButton button;
	protected Connection connection;
	protected Channel channel;

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
	
	protected void createQueue(String host, String... queueNames) throws IOException{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(host);
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    
	    for (String queue : queueNames) {
	    	channel.queueDeclare(queue, false, false, false, null);
		}
	    
	}
	
	protected void disposeQueue() throws IOException{
		channel.close();
		connection.close();
	}

}
