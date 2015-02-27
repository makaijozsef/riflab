package hu.bme.mit.riflab.cjwc0f.workflow.ui;

import hu.bme.mit.riflab.cjwc0f.workflow.Workflow;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class WorkflowUI extends JFrame {

	private static final long serialVersionUID = 1L;

	protected JTextArea textApply;
	protected JTextArea textAverage;
	protected JTextArea textSocial;
	protected JTextArea textIncrementAverage;
	protected JTextArea textAssignRoom;
	protected JTextArea textFinalResult;
	private JButton button;

	public WorkflowUI() {
		super("Workflow with OSGi");
		Container contentPane = this.getContentPane();
		Insets insets = contentPane.getInsets();

		setPreferredSize(new Dimension(1000, 600));
		setResizable(false);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		textApply = new JTextArea();
		textAverage = new JTextArea();
		textSocial = new JTextArea();
		textIncrementAverage = new JTextArea();
		textAssignRoom = new JTextArea();
		textFinalResult = new JTextArea();

		JLabel textApplyJLabel = new JLabel("Applicant data");
		JLabel textAverageJLabel = new JLabel("Calculated average");
		JLabel textSocialJLabel = new JLabel("Social result");
		JLabel textIncrementAverageJLabel = new JLabel("Community points calculated");
		JLabel textAssignRoomJLabel = new JLabel("Room number assigned");
		JLabel textFinalResultJLabel = new JLabel("Final application result");

		contentPane.add(textApplyJLabel);
		contentPane.add(textAverageJLabel);
		contentPane.add(textSocialJLabel);
		contentPane.add(textIncrementAverageJLabel);
		contentPane.add(textAssignRoomJLabel);
		contentPane.add(textFinalResultJLabel);

		contentPane.add(textApply);
		contentPane.add(textAverage);
		contentPane.add(textSocial);
		contentPane.add(textIncrementAverage);
		contentPane.add(textAssignRoom);
		contentPane.add(textFinalResult);

		contentPane.setLayout(null);

		textApplyJLabel.setBounds(insets.left, 140 + insets.top, 200, 10);
		textApply.setBounds(insets.left, 150 + insets.top, 200, 200);
		textAverageJLabel.setBounds(200 + insets.left, 25 + insets.top, 200, 10);
		textAverage.setBounds(200 + insets.left, 35 + insets.top, 200, 200);
		textSocialJLabel.setBounds(400 + insets.left, 255 + insets.top, 200, 10);
		textSocial.setBounds(400 + insets.left, 265 + insets.top, 200, 200);
		textIncrementAverageJLabel.setBounds(400 + insets.left, 25 + insets.top, 200, 10);
		textIncrementAverage.setBounds(400 + insets.left, 35 + insets.top, 200, 200);
		textAssignRoomJLabel.setBounds(600 + insets.left, insets.top, 200, 10);
		textAssignRoom.setBounds(600 + insets.left, insets.top + 10, 200, 200);
		textFinalResultJLabel.setBounds(800 + insets.left, 140 + insets.top, 200, 10);
		textFinalResult.setBounds(800 + insets.left, 150 + insets.top, 200, 200);

		button = new JButton();
		getButton().setText("Next step");
		contentPane.add(getButton());

		getButton().setBounds(insets.left + 450, insets.top + 500, 100, 50);
		getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				synchronized (Workflow.syncObject) {
					Workflow.syncObject.notify();
				}
			}
		});

	}

	public void setApplicantData(String data) {
		textFinalResult.setBackground(Color.WHITE);
		textApply.setText(data);
		textAverage.setText("");
		textSocial.setText("");
		textIncrementAverage.setText("");
		textAssignRoom.setText("");
		textFinalResult.setText("");
		textApply.setBackground(Color.RED);
	}

	public void setDeterminedAverage(String data) {
		textApply.setBackground(Color.WHITE);
		textAverage.setText(data);
		textAverage.setBackground(Color.RED);
	}

	public void setSocialResults(String data) {
		textAverage.setBackground(Color.WHITE);
		textSocial.setText(data);
		textSocial.setBackground(Color.RED);
	}

	public void setCalculatedCommunityPoints(String data) {
		textSocial.setBackground(Color.WHITE);
		textIncrementAverage.setText(data);
		textIncrementAverage.setBackground(Color.RED);
	}

	public void setRoomNumber(String data) {
		textIncrementAverage.setBackground(Color.WHITE);
		textAssignRoom.setText(data);
		textAssignRoom.setBackground(Color.RED);
	}

	public void setFinalResults(String data) {
		textIncrementAverage.setBackground(Color.WHITE);
		textAssignRoom.setBackground(Color.WHITE);
		textFinalResult.setText(data);
		textFinalResult.setBackground(Color.RED);
	}

	public JButton getButton() {
		return button;
	}

	@Override
	public void dispose() {
		Workflow.execute = false;
		synchronized (Workflow.syncObject) {
			Workflow.syncObject.notify();
		}
		super.dispose();
	}
}
