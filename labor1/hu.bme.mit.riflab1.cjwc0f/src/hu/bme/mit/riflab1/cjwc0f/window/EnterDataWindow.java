package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.EnterDataWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class EnterDataWindow extends AbstractWindow {

	public EnterDataWindow(EnterDataWorker enterDataWorker) {
		super("Enter applicant data", 0, 300);

		enterDataWorker.addTextArea(textArea);

		enterDataWorker.execute();

		JButton button = new JButton();
		button.setText("GenerateData");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enterDataWorker.clicked();
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}

}
