package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.SocialInspectionWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SocialInspectionWindow extends AbstractWindow {

	public SocialInspectionWindow(SocialInspectionWorker socialInspectionWorker) {
		super("Social inspection", 700, 500);

		socialInspectionWorker.addTextArea(textArea);

		socialInspectionWorker.execute();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				socialInspectionWorker.clicked();
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}

}
