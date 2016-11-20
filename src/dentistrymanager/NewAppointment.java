package dentistrymanager;

import java.awt.Dimension;
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

	// UI variables
	private JPanel contentPane;
	private JTextField patientNameField;
	private JLabel lblDate;
	private JLabel lblStartTime;
	private JTextField startTimeField;
	private JLabel lblEndTime;
	private JTextField endTimeField;
	private JLabel lblPartner;
    private JComboBox<String> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<String> yearCombo;
    private JComboBox<String> typeOfTreatmentCombo;
    private JComboBox<String> partnerCombo;
    
    // Other variables
    private Patient patient;
    private String[] typesStr;
    private String[] partnersStr;

    /*
     * Checks if any of the text fields are empty
     */
    public boolean formFilled() {
    	if (startTimeField.getText().trim().isEmpty() || endTimeField.getText().trim().isEmpty())
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
			long date = Long.valueOf(yearCombo.getSelectedItem().toString() + monthCombo.getSelectedItem().toString() + dayCombo.getSelectedItem().toString());
			String partner = typeOfTreatmentCombo.getSelectedItem().toString();
			int startTime = Integer.valueOf(startTimeField.getText());
			int endTime = Integer.valueOf(endTimeField.getText());
			String typeOfT = partnerCombo.getSelectedItem().toString();
			int courseOfT = 0;//getCourseOfTreatment(); #TODO
			Appointment app = new Appointment(partner, date, startTime, endTime, patient, typeOfT, courseOfT);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		return success;
    }
    
	/**
	 * Create the frame.
	 */
	public NewAppointment(Patient p) {
		this.patient = p;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPatientName = new JLabel("Name:");
		patientNameField = new JTextField();
		patientNameField.setEditable(false);
		patientNameField.setColumns(10);
		if (patient!=null)
			patientNameField.setText(patient.getForename()+" "+patient.getSurname());
		
		
		// Date section
		lblDate = new JLabel("Date:");
		yearCombo = new JComboBox<String>();
		monthCombo = new JComboBox<String>();
		dayCombo = new JComboBox<String>();
		
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
		for (int i=now.get(Calendar.YEAR); i<=now.get(Calendar.YEAR)+2; i++)
			years.add(String.valueOf(i));		
		String[] y = new String[years.size()];
		y = years.toArray(y);

        dayCombo.setModel(new DefaultComboBoxModel<String>(days));
        monthCombo.setModel(new DefaultComboBoxModel<String>(months));
        yearCombo.setModel(new DefaultComboBoxModel<String>(y));
		
        
        // Time section
		lblStartTime = new JLabel("Start time: ");
		startTimeField = new JTextField();
		startTimeField.setColumns(10);
		
		lblEndTime = new JLabel("End time: ");
		endTimeField = new JTextField();
		endTimeField.setColumns(10);
		
		// Select partner section
		lblPartner = new JLabel("Physician:");
		partnerCombo = new JComboBox<String>();
		try(Connection con = DBConnect.getConnection(false)){
			ArrayList<Partner> partners = Partner.getAll(con);
			partnersStr = new String[partners.size()];
			for (Partner pa : partners)
				partnersStr[partners.indexOf(pa)] = (pa.toString());
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		partnerCombo.setModel(new DefaultComboBoxModel<String>(partnersStr));
		
		// Treatment section
		JLabel lblTypeOfTreatment = new JLabel("Type of Treatment:");
		typeOfTreatmentCombo = new JComboBox<String>();
		try(Connection con = DBConnect.getConnection(false)){
			ArrayList<TypeOfTreatment> types = TypeOfTreatment.getAll(con);
			typesStr = new String[types.size()];
			for (TypeOfTreatment tot : types)
				typesStr[types.indexOf(tot)] = (tot.toString());

		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
        typeOfTreatmentCombo.setModel(new DefaultComboBoxModel<String>(typesStr));
		
		// Buttons
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formFilled()) {
					if (updateDB())
						JOptionPane.showMessageDialog(new JFrame(), "Appointment Added");
					else
					    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in adding this appointment. Please try again.",
					    		"Submission Error", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.", "Submission Error",
				            JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		// Generated code - do not modify
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCancel)
							.addGap(18)
							.addComponent(btnSubmit))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPatientName)
								.addComponent(lblStartTime)
								.addComponent(lblDate))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(patientNameField, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(startTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
									.addComponent(lblEndTime)
									.addGap(18)
									.addComponent(endTimeField, 78, 78, 78))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(dayCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(monthCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(yearCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPartner)
								.addComponent(lblTypeOfTreatment))
							.addGap(14)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(partnerCombo, 0, 233, Short.MAX_VALUE)
								.addComponent(typeOfTreatmentCombo, Alignment.LEADING, 0, 233, Short.MAX_VALUE))))
					.addContainerGap())
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
						.addComponent(endTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndTime))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPartner)
						.addComponent(partnerCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(typeOfTreatmentCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTypeOfTreatment))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSubmit)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
		pack();
	}
}