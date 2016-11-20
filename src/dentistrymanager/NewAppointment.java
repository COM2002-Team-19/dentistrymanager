package dentistrymanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
	private JLabel lblEndTime;
	private JLabel lblPartner;
    private JComboBox<String> dayCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<String> yearCombo;
    private JComboBox<String> startTimeCombo;
    private JComboBox<String> endTimeCombo;
    private JComboBox<String> partnerCombo;
    private JComboBox<String> typeOfTreatmentCombo;
    
    // Other variables
    private Patient patient;
    private String[] partnersStr;
    private String selectedPartner;
    private String[] typesStr;
    
    /*
     * Uploads new patient to servers
     * @return boolean whether or not patient upload successful
     */
    public boolean updateDB() {
		boolean success = false;
		Appointment app = createAppFromForm();
		//if (app.)
		try(Connection connection = DBConnect.getConnection(true)){

			success = app.add(connection);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
			success = false;
		} catch (DuplicateKeyException e) {
		    JOptionPane.showMessageDialog(new JFrame(), "There has been an error with the database, please see admin.", "Submission Error",
		            JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			success = false;
		}
		
		return success;
    }
    
	/**
	 * Create the frame.
	 */
	public NewAppointment(Patient p) {
		patient = p;
		selectedPartner = "";
		
		// Initialise Frame
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
		lblEndTime = new JLabel("End time: ");
		startTimeCombo = new JComboBox<String>();
		endTimeCombo = new JComboBox<String>();
		
		//Create arrays of time values
		int numOfTimes = (17-9)*3;
		String[] startTimes = new String[numOfTimes];
		int c = 0;
		for (int h=900; h<1700; h+=100){
			for (int m=0; m<60; m+=20){
				String t;
				if (h+m < 1000)
					t = "0"+Integer.toString(h+m);
				else
					t = Integer.toString(h+m);
					startTimes[c] = t;
				c++;
			}
		}
		String[] endTimes = new String[numOfTimes];
		for (int i=0; i<numOfTimes-1; i++)
			endTimes[i] = startTimes[i+1];
		endTimes[numOfTimes-1] = "1700";
		
		startTimeCombo.setModel(new DefaultComboBoxModel<String>(startTimes));
		endTimeCombo.setModel(new DefaultComboBoxModel<String>(endTimes));
		
		// Select partner section
		lblPartner = new JLabel("Partner:");
		partnerCombo = new JComboBox<String>();
		partnerCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPartner = (String)partnerCombo.getSelectedItem();
				updateTreatmentList();
			}
		});
		updatePartnerList();
		
		// Treatment section
		JLabel lblTypeOfTreatment = new JLabel("Type of Treatment:");
		typeOfTreatmentCombo = new JComboBox<String>();
		updateTreatmentList();
		
		// Buttons
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (updateDB())
					JOptionPane.showMessageDialog(new JFrame(), "Appointment Added");
				else
				    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in adding this appointment. Please try again.",
				    		"Submission Error", JOptionPane.ERROR_MESSAGE);
				dispose();
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
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(dayCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(monthCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(yearCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(startTimeCombo, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
									.addGap(24)
									.addComponent(lblEndTime)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(endTimeCombo, 0, 86, Short.MAX_VALUE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPartner)
								.addComponent(lblTypeOfTreatment))
							.addGap(14)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(partnerCombo, 0, 236, Short.MAX_VALUE)
								.addComponent(typeOfTreatmentCombo, Alignment.LEADING, 0, 236, Short.MAX_VALUE))))
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
						.addComponent(lblEndTime)
						.addComponent(startTimeCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(endTimeCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
	
	// Ensures entered date and time values are reasonable
	private void validateSlot() {
		//#TODO
	}
	
	// Extracts data from frame/form and returns an Appointment instance
	private Appointment createAppFromForm() {
		Date date = DateTimeUtilities.stringToDate(yearCombo.getSelectedItem().toString(), 
				monthCombo.getSelectedItem().toString() ,
				dayCombo.getSelectedItem().toString());
		String partner = typeOfTreatmentCombo.getSelectedItem().toString();
		Time startTime = DateTimeUtilities.stringToTime(startTimeCombo.getSelectedItem().toString());
		Time endTime = DateTimeUtilities.stringToTime(endTimeCombo.getSelectedItem().toString());
		String typeOfT = partnerCombo.getSelectedItem().toString();
		int courseOfT = 0;//getCourseOfTreatment(); #TODO
		
		return new Appointment(partner, date, startTime, endTime, patient, typeOfT, courseOfT);
	}
	
	private void updatePartnerList() {
		try(Connection con = DBConnect.getConnection(false)){
			ArrayList<Partner> partners = Partner.getAll(con);
			partnersStr = new String[partners.size()];
			for (Partner pa : partners)
				partnersStr[partners.indexOf(pa)] = (pa.toString());
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		partnerCombo.setModel(new DefaultComboBoxModel<String>(partnersStr));
		selectedPartner = partnerCombo.getSelectedItem().toString();
	}
	
	private void updateTreatmentList() {
		try(Connection con = DBConnect.getConnection(false)){
			ArrayList<TypeOfTreatment> types = TypeOfTreatment.getAllByPartner(con, selectedPartner);
			typesStr = new String[types.size()];
			for (TypeOfTreatment tot : types)
				typesStr[types.indexOf(tot)] = (tot.toString());

		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
        typeOfTreatmentCombo.setModel(new DefaultComboBoxModel<String>(typesStr));
	}
}