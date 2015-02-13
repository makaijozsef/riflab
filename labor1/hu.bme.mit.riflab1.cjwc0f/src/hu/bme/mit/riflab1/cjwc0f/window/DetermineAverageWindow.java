package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.DetermineAverageWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DetermineAverageWindow extends AbstractWindow {

	public DetermineAverageWindow(DetermineAverageWorker determineAverageWorker) {
		super("Determine average", 350, 100);

		determineAverageWorker.addTextArea(textArea);
		determineAverageWorker.execute();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				determineAverageWorker.clicked();
			}

		});

	}

}
