package hu.bme.mit.riflab1.cjwc0f.window;

import hu.bme.mit.riflab1.cjwc0f.workers.EnterDataWorker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class EnterDataWindow extends AbstractWindow{

	private static final long serialVersionUID = 3714604390937043362L;

	public EnterDataWindow(EnterDataWorker enterDataWorker) {
		super("Enter applicant data");		
		
		JLabel label = new JLabel();
		this.getContentPane().add(label, BorderLayout.NORTH);
		enterDataWorker.addLabel(label);
		
		enterDataWorker.execute();
		
		JButton button = new JButton();
		button.setText("GenerateData");
		button.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enterDataWorker.clicked();
			}
			
		});
		this.getContentPane().add(button, BorderLayout.SOUTH);
	}
	
}
