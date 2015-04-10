package hu.bme.mit.cjwc0f.labor4.detfinalresult;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
import hu.bme.mit.cjwc0f.labor5.gui.AbstractWindow;
import hu.bme.mit.cjwc0f.labor5.workflow.DetermineFinalResult;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class DetermineFinalResultWindow extends AbstractWindow {

	private List<ApplicationData> applicationDatas = new ArrayList<ApplicationData>();
	private List<SocialResult> socialResults = new ArrayList<SocialResult>();

	private JButton socialButton;

	public DetermineFinalResultWindow(Queue<Serializable> inQueueStudy, Queue<Serializable> inQueueSocial) {

		super("Determine final result", 1400, 300);

		final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
		this.setTitle("Determine final result - " + jvmName);

		button.setText("Consume from study part");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ApplicationData applicationData = (ApplicationData) inQueueStudy.poll();

				SocialResult matchingSR = null;
				for (SocialResult sr : socialResults) {
					if (applicationData.getTimestamp().equals(sr.getApplicantData().getTimestamp())) {
						matchingSR = sr;
						break;
					}
				}
				if (matchingSR != null) {
					socialResults.remove(matchingSR);
					ApplicationData finalResult = DetermineFinalResult.decide(applicationData, matchingSR);

					textArea.setText(finalResult.toString());
				} else {
					applicationDatas.add(applicationData);
				}
			}

		});

		this.getContentPane().add(button, BorderLayout.WEST);

		socialButton = new JButton();
		socialButton.setText("Consume from social part");

		socialButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SocialResult socialResult = (SocialResult) inQueueSocial.poll();

				ApplicationData matchingAD = null;
				for (ApplicationData ad : applicationDatas) {
					if (ad.getTimestamp().equals(socialResult.getApplicantData().getTimestamp())) {
						matchingAD = ad;
						break;
					}
				}
				if (matchingAD != null) {
					applicationDatas.remove(matchingAD);
					ApplicationData finalResult = DetermineFinalResult.decide(matchingAD, socialResult);

					textArea.setText(finalResult.toString());
				} else {
					socialResults.add(socialResult);
				}
			}

		});

		this.getContentPane().add(socialButton, BorderLayout.EAST);

		Thread t1 = new Thread(new QueueObserver(socialButton, inQueueSocial));
		Thread t2 = new Thread(new QueueObserver(button, inQueueStudy));
		t1.setDaemon(true);
		t2.setDaemon(true);
		t1.start();
		t2.start();

	}

}
