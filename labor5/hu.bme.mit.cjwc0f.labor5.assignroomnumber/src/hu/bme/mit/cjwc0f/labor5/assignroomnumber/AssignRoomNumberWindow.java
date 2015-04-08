package hu.bme.mit.cjwc0f.labor5.assignroomnumber;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.AssignRoomNumber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.management.ManagementFactory;

@SuppressWarnings("serial")
public class AssignRoomNumberWindow extends AbstractWindow {


	public AssignRoomNumberWindow() {
		super("Assign room number", 1050, 0);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Assign room number - " + jvmName);
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		
		});

	}

}
