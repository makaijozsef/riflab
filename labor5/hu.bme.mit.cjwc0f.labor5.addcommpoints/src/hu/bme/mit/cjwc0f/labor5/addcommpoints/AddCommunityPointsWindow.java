package hu.bme.mit.cjwc0f.labor5.addcommpoints;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.AddCommunityPoints;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class AddCommunityPointsWindow extends AbstractWindow {

	private JButton buttonTrue;
	private JButton buttonFalse;

	public AddCommunityPointsWindow() {
		super("Add community points", 700, 100);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Add community points - " + jvmName);
		
		
		button.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});


		buttonTrue = new JButton();
		buttonTrue.setText("Go to room assignment");
		buttonTrue.addActionListener(new ActionListener() {


			@Override
			public void actionPerformed(ActionEvent e) {

			}

		});
		this.getContentPane().add(buttonTrue, BorderLayout.EAST);

		buttonFalse = new JButton();
		buttonFalse.setText("Skip room assignment");
		buttonFalse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}

		});
		this.getContentPane().add(buttonFalse, BorderLayout.WEST);

	}

}
