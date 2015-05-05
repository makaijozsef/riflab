package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.DetermineFinalResultWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class DetermineFinalResultWindow extends AbstractWindow {

	public DetermineFinalResultWindow(DetermineFinalResultWorker determineFinalResultWorker) {
		super("Determine final result", 1400, 300);

		determineFinalResultWorker.addTextArea(textArea);
		determineFinalResultWorker.execute();

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				determineFinalResultWorker.clicked();
			}

		});

	}

}
