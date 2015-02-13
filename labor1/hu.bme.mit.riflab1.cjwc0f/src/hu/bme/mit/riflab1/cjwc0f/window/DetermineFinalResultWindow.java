package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.DetermineFinalResultWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class DetermineFinalResultWindow extends AbstractWindow {

	private static final long serialVersionUID = 3714604390937043362L;

	public DetermineFinalResultWindow(DetermineFinalResultWorker determineFinalResultWorker) {
		super("Determine final result", 1500, 200, 100, 100);

		JLabel label = new JLabel();
		this.getContentPane().add(label, BorderLayout.NORTH);
		determineFinalResultWorker.addLabel(label);

		determineFinalResultWorker.execute();

		JButton button = new JButton();
		button.setText("Determine final result");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					determineFinalResultWorker.clicked();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}

}
