package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.DetermineAverageWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetermineAverageWindow extends AbstractWindow {

	private static final long serialVersionUID = 3714604390937043362L;

	public DetermineAverageWindow(DetermineAverageWorker determineAverageWorker) {
		super("Determine average", 350, 100, 100, 100);

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
