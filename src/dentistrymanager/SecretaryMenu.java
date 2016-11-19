package dentistrymanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import javax.swing.GroupLayout.Alignment;

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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        calendarButton.setText("Calendars");
        registerButton.setText("Register Patient");
        findPatientButton.setText("Find Patient");
        
        calendarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SecretaryCalendar sc = new SecretaryCalendar();
			}
		});
        registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPatient sc = new RegisterPatient();
			}
		});
        findPatientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindPatient sc = new FindPatient();
			}
		});

        // Generated code - do not modify
        GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
        gl_buttonPanel.setHorizontalGroup(
        	gl_buttonPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_buttonPanel.createSequentialGroup()
        			.addGroup(gl_buttonPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_buttonPanel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(registerButton))
        				.addGroup(gl_buttonPanel.createSequentialGroup()
        					.addGap(20)
        					.addComponent(findPatientButton))
        				.addGroup(gl_buttonPanel.createSequentialGroup()
        					.addGap(23)
        					.addComponent(calendarButton)))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_buttonPanel.setVerticalGroup(
        	gl_buttonPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_buttonPanel.createSequentialGroup()
        			.addGap(70)
        			.addComponent(calendarButton)
        			.addGap(18)
        			.addComponent(registerButton)
        			.addGap(18)
        			.addComponent(findPatientButton)
        			.addContainerGap(84, Short.MAX_VALUE))
        );
        buttonPanel.setLayout(gl_buttonPanel);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(150)
        			.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
        			.addContainerGap())
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