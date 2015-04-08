package hu.bme.mit.cjwc0f.labor5.socialinspection;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.SocialInspection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class SocialInspectionWindow extends AbstractWindow {
	
	
	public SocialInspectionWindow() {
		super("Social inspection", 700, 500);
		
		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Social inspection - " + jvmName);
		
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});

	}

}
