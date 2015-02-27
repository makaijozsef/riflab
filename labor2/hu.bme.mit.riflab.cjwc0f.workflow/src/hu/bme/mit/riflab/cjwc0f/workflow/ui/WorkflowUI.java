package hu.bme.mit.riflab.cjwc0f.workflow.ui;

import hu.bme.mit.riflab.cjwc0f.workflow.Workflow;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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

		setPreferredSize(new Dimension(1000, 600));
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textApply = new JTextArea();
		textAverage = new JTextArea();
		textSocial = new JTextArea();
		textIncrementAverage = new JTextArea();
		textAssignRoom = new JTextArea();
		textFinalResult = new JTextArea();
		Container contentPane = this.getContentPane();

		contentPane.add(textApply);
		contentPane.add(textAverage);
		contentPane.add(textSocial);
		contentPane.add(textIncrementAverage);
		contentPane.add(textAssignRoom);
		contentPane.add(textFinalResult);

		contentPane.setLayout(null);

		Insets insets = contentPane.getInsets();
		textApply.setBounds(insets.left, 150 + insets.top, 200, 200);
		textAverage.setBounds(200 + insets.left, 25 + insets.top, 200, 200);
		textSocial.setBounds(400 + insets.left, 275 + insets.top, 200, 200);
		textIncrementAverage.setBounds(400 + insets.left, 25 + insets.top, 200, 200);
		textAssignRoom.setBounds(600 + insets.left, insets.top, 200, 200);
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
		textApply.setText(data);
		textAverage.setText("");
		textSocial.setText("");
		textIncrementAverage.setText("");
		textAssignRoom.setText("");
		textFinalResult.setText("");
	}

	public void setDeterminedAverage(String data) {
		textAverage.setText(data);
	}

	public void setCalculatedCommunityPoints(String data) {
		textIncrementAverage.setText(data);
	}

	public void setRoomNumber(String data) {
		textAssignRoom.setText(data);
	}

	public void setSocialResults(String data) {
		textSocial.setText(data);
	}

	public void setFinalResults(String data) {
		textFinalResult.setText(data);
	}

	public JButton getButton() {
		return button;
	}

}
