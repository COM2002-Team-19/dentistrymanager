package dentistrymanager.gui;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestSplash extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestSplash() {
		
		JButton btnSubscribe = new JButton("Subscribe");
		btnSubscribe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnSubscribe);

	}

}
