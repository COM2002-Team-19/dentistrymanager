package dentistrymanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@SuppressWarnings("serial")
public class RegisterPatient extends JFrame {

    /**
     * Creates new form RegisterPatient
     */
    public RegisterPatient() {
        initComponents();
    }
    
    /*
     * Uploads new patient to servers
     * @return boolean whether or not patient upload successful
     */
    public boolean updateDB() {
		boolean success = false;
		
		try(Connection connection = DBConnect.getConnection(true)){
			String t = titleCombo.getSelectedItem().toString();
			String fName = forenameField.getText().trim();
			String sName = surnameField.getText().trim();
			Date dob = DateTimeUtilities.stringToDate(yearCombo.getSelectedItem().toString(), 
						monthCombo.getSelectedItem().toString(), 
						dayCombo.getSelectedItem().toString());
			
			String phoneNo = phoneField.getText().trim();
			Address a = new Address(Integer.valueOf(houseNumberField.getText()),streetField.getText().trim(),
									cityField.getText().trim(),districtField.getText().trim(),postcodeField.getText().trim()); 
			Patient p = new Patient(t,fName,sName,dob,phoneNo,a);
			
			boolean executeNext = false;
			try{
				executeNext = a.add(connection);
			} catch (DuplicateKeyException e) {
				executeNext = true;
			}
			
			if(executeNext)
				success = p.add(connection);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		return success;
    }
    
    /*
     * Checks if any of the text fields are empty
     */
    public boolean formFilled() {
    	if (forenameField.getText().trim().isEmpty() 
    			|| surnameField.getText().trim().isEmpty()
    			|| phoneField.getText().trim().isEmpty()
    			|| houseNumberField.getText().trim().isEmpty()
    			|| streetField.getText().trim().isEmpty()
    			|| cityField.getText().trim().isEmpty()
    			|| districtField.getText().trim().isEmpty()
    			|| postcodeField.getText().trim().isEmpty())
    		return false;
    	return true;
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    // initialise components
    private void initComponents() {
        titleLabel = new JLabel();
        titleCombo = new JComboBox<>();
        firstNameLabel = new JLabel();
        forenameField = new JTextField();
        surnameLabel = new JLabel();
        surnameField = new JTextField();
        dateOfBirthLabel = new JLabel();
        dayCombo = new JComboBox<>();
        monthCombo = new JComboBox<>();
        yearCombo = new JComboBox<>();
        phoneLabel = new JLabel();
        phoneField = new JTextField();
        addressPanel = new JPanel();
        submitButton = new JButton();
        cancelButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Title section
        Title[] titles =  Title.values();
        String[] titlesStr = new String[titles.length];
        for (int i=0; i<titles.length; i++)
        	titlesStr[i] = titles[i].toString();
        
        titleLabel.setText("Title:");
        titleCombo.setModel(new DefaultComboBoxModel<String>(titlesStr));
        
        firstNameLabel.setText("Firstname:");
        surnameLabel.setText("Surname:");
        dateOfBirthLabel.setText("Date of birth:");
        
		//Create arrays of date values
		String[] days = new String[31];
		for (int i=1; i<10; i++)
			days[i-1] = "0" + String.valueOf(i);
		for (int i=10; i<=31; i++)
			days[i-1] = String.valueOf(i);
		
		String[] months = new String[12];
		for (int i=1; i<10; i++)
			months[i-1] = "0" + String.valueOf(i);
		for(int i=10; i<=12; i++)
			months[i-1] = String.valueOf(i);
		
		ArrayList<String> years = new ArrayList<String>();
		Calendar now = Calendar.getInstance();
		for (int i=1900; i<=now.get(Calendar.YEAR); i++)
			years.add(String.valueOf(i));		
		String[] y = new String[years.size()];
		y = years.toArray(y);

        dayCombo.setModel(new DefaultComboBoxModel<String>(days));
        monthCombo.setModel(new DefaultComboBoxModel<String>(months));
        yearCombo.setModel(new DefaultComboBoxModel<String>(y));

        phoneLabel.setText("Phone number:");

        addressPanel.setBorder(BorderFactory.createEtchedBorder());
        
        submitButton.setText("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formFilled()) {
					if (updateDB())
						JOptionPane.showMessageDialog(new JFrame(), "Registration Success");
					else
					    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in registering this patient." 
					    		+"Please check your connection and try again.", "Submission Error", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.", "Submission Error",
				            JOptionPane.ERROR_MESSAGE);
			}
		});
        cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
        
        houseNumberLabel = new JLabel("House Number");
        houseNumberField = new JTextField();
        houseNumberField.setColumns(10);
        streetLabel = new JLabel("Street");
        streetField = new JTextField();
        streetField.setColumns(10);
        cityLabel = new JLabel("City");
        cityField = new JTextField();
        cityField.setColumns(10);
        districtLabel = new JLabel("District");
        districtField = new JTextField();
        districtField.setColumns(10);
        postcodeLabel = new JLabel("Postcode");
        postcodeField = new JTextField();
        postcodeField.setColumns(10);

        // Generated code - do not modify
        GroupLayout addressPanelLayout = new GroupLayout(addressPanel);
        addressPanelLayout.setHorizontalGroup(
        	addressPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(addressPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(houseNumberLabel)
        				.addComponent(streetLabel)
        				.addComponent(cityLabel)
        				.addComponent(districtLabel)
        				.addComponent(postcodeLabel))
        			.addGap(94)
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(streetField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        				.addComponent(houseNumberField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        				.addComponent(cityField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        				.addComponent(districtField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        				.addComponent(postcodeField, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        			.addContainerGap())
        );
        addressPanelLayout.setVerticalGroup(
        	addressPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(addressPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(houseNumberLabel)
        				.addComponent(houseNumberField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(streetLabel)
        				.addComponent(streetField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(cityLabel)
        				.addComponent(cityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(districtLabel)
        				.addComponent(districtField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(addressPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(postcodeLabel)
        				.addComponent(postcodeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(14, Short.MAX_VALUE))
        );
        addressPanel.setLayout(addressPanelLayout);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(addressPanel, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        				.addComponent(phoneLabel)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED))
        						.addGroup(layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.LEADING)
        								.addComponent(surnameLabel)
        								.addComponent(dateOfBirthLabel)
        								.addComponent(firstNameLabel)
        								.addComponent(titleLabel))
        							.addGap(112)
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(dayCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(monthCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(yearCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        								.addGroup(layout.createSequentialGroup()
        									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        										.addComponent(surnameField, Alignment.TRAILING)
        										.addComponent(forenameField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
        										.addComponent(phoneField, Alignment.TRAILING))
        									.addPreferredGap(ComponentPlacement.RELATED))
        								.addGroup(layout.createSequentialGroup()
        									.addComponent(titleCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        									.addPreferredGap(ComponentPlacement.RELATED)))))
        					.addGap(0)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(titleLabel)
        				.addComponent(titleCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(firstNameLabel)
        				.addComponent(forenameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(3)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(surnameLabel)
        				.addComponent(surnameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(monthCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(yearCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(dateOfBirthLabel)
        				.addComponent(dayCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(phoneLabel)
        				.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(addressPanel, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(submitButton)
        				.addComponent(cancelButton))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        getContentPane().setLayout(layout);

        pack();
        setVisible(true);
    }

    // Variables declaration - do not modify
    private JPanel addressPanel;
    private JButton cancelButton;
    private JLabel dateOfBirthLabel;
    private JLabel firstNameLabel;
    private JComboBox<String> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<String> yearCombo;
    private JTextField forenameField;
    private JLabel phoneLabel;
    private JTextField phoneField;
    private JButton submitButton;
    private JTextField surnameField;
    private JLabel surnameLabel;
    private JComboBox<String> titleCombo;
    private JLabel titleLabel;
    private JLabel houseNumberLabel;
    private JTextField houseNumberField;
    private JLabel streetLabel;
    private JTextField streetField;
    private JLabel cityLabel;
    private JTextField cityField;
    private JLabel districtLabel;
    private JTextField districtField;
    private JLabel postcodeLabel;
    private JTextField postcodeField;
}