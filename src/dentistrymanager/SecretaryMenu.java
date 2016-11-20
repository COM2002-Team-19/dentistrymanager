package dentistrymanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SecretaryMenu extends JFrame {

    /**
     * Constructor
     */
    public SecretaryMenu() {
    	setResizable(false);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // initialise Components
    private void initComponents() {

        buttonPanel = new JPanel();
        calendarButton = new JButton();
        registerButton = new JButton();
        findPatientButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        calendarButton.setText("Calendars");
        registerButton.setText("Register Patient");
        findPatientButton.setText("Find Patient");
        
        calendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SecretaryCalendar();
			}
		});
        registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RegisterPatient();
			}
		});
        findPatientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FindPatient();
			}
		});

        // Generated code - do not modify
        GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
        gl_buttonPanel.setHorizontalGroup(
        	gl_buttonPanel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, gl_buttonPanel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(calendarButton, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
        			.addGap(16)
        			.addComponent(findPatientButton, GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
        			.addGap(18)
        			.addComponent(registerButton)
        			.addGap(12))
        );
        gl_buttonPanel.setVerticalGroup(
        	gl_buttonPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_buttonPanel.createSequentialGroup()
        			.addGap(54)
        			.addGroup(gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(findPatientButton)
        				.addComponent(registerButton)
        				.addComponent(calendarButton))
        			.addContainerGap(41, Short.MAX_VALUE))
        );
        buttonPanel.setLayout(gl_buttonPanel);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(66)
        			.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(97, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
        setVisible(true);
    }

    // Variables declaration
    private JButton calendarButton;
    private JButton registerButton;
    private JButton findPatientButton;
    private JPanel buttonPanel;
}