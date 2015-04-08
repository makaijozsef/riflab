package hu.bme.mit.cjwc0f.labor4.detfinalresult;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.DetermineFinalResult;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class DetermineFinalResultWindow extends AbstractWindow {
	
	
	
	private List<ApplicationData> applicationDatas = new ArrayList<ApplicationData>();
	private List<SocialResult> socialResults = new ArrayList<SocialResult>();

	private JButton socialButton;
	
	public DetermineFinalResultWindow() {
		
		super("Determine final result", 1400, 300);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine final result - " + jvmName);
		

	    button.setText("Consume from study part");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}

		});
		
		this.getContentPane().add(button, BorderLayout.WEST);
		
		socialButton = new JButton();
		socialButton.setText("Consume from social part");
		
		socialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}

		});

		this.getContentPane().add(socialButton, BorderLayout.EAST);

	}

}
