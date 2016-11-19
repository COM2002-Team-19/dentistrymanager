package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class NewAppointment extends JFrame {

	private JPanel contentPane;
	private JTextField patientNameField;
	private JLabel lblDate;
	private JLabel lblStartTime;
	private JTextField startTimeField;
	private JLabel lblEndTime;
	private JTextField endTimeField;
	private JLabel lblTypeOfTreatment;
	private JTextField treatmentTypeField;
	private JLabel lblCourseOfTreatment;
	private JTextField treatmentCourseField;
    private JComboBox<String> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<String> yearCombo;

    /*
     * Checks if any of the text fields are empty
     */
    public boolean formFilled() {
    	if (patientNameField.getText().trim().isEmpty())
    		return false;
    	return true;
    }
    
    /*
     * Uploads new patient to servers
     * @return boolean whether or not patient upload successful
     */
    public boolean updateDB() {
		boolean success = false;
		
		try(Connection connection = DBConnect.getConnection(true)){
			String pName = patientNameField.getText().trim();
			long dob = Long.valueOf(yearCombo.getSelectedItem().toString() + monthCombo.getSelectedItem().toString() + dayCombo.getSelectedItem().toString());
			//Appointment app = new Appointment();
			/*
			boolean executeNext = false;
			try{
				executeNext = app.add(connection);
			} catch (DuplicateKeyException e) {
				
			}
			*/
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		return success;
    }
    
	/**
	 * Create the frame.
	 */
	public NewAppointment(Patient p) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 270);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPatientName = new JLabel("Name:");
		patientNameField = new JTextField();
		patientNameField.setColumns(10);
		patientNameField.setText(p.getForename()+" "+p.getSurname());
		
		lblDate = new JLabel("Date :");
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
		
		lblStartTime = new JLabel("Start time: ");
		
		startTimeField = new JTextField();
		startTimeField.setColumns(10);
		
		lblEndTime = new JLabel("End time: ");
		
		endTimeField = new JTextField();
		endTimeField.setColumns(10);
		
		lblTypeOfTreatment = new JLabel("Type of Treatment: ");
		
		treatmentTypeField = new JTextField();
		treatmentTypeField.setColumns(10);
		
		lblCourseOfTreatment = new JLabel("Course of Treatment: ");
		
		treatmentCourseField = new JTextField();
		treatmentCourseField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formFilled()) {
					if (updateDB())
						JOptionPane.showMessageDialog(new JFrame(), "Appointment Added");
					else
					    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in adding this appointment. Please try again.", "Submission Error",
					            JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.", "Submission Error",
				            JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		yearCombo = new JComboBox();
		monthCombo = new JComboBox();
		dayCombo = new JComboBox();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnBack)
							.addGap(18)
							.addComponent(btnSubmit))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPatientName)
								.addComponent(lblStartTime)
								.addComponent(lblDate))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(startTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblEndTime)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(endTimeField))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(dayCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(monthCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(yearCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addComponent(patientNameField, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTypeOfTreatment)
								.addComponent(lblCourseOfTreatment))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(treatmentCourseField, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(treatmentTypeField, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))))
					.addGap(62))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPatientName)
						.addComponent(patientNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(yearCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(monthCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(dayCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDate))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartTime)
						.addComponent(startTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndTime)
						.addComponent(endTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypeOfTreatment)
						.addComponent(treatmentTypeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCourseOfTreatment)
						.addComponent(treatmentCourseField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSubmit)
						.addComponent(btnBack))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}