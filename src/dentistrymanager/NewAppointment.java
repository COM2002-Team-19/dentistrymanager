package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
	private JTextField forenameField;
	private JTextField surnameField;
	private JLabel lblDate;
	private JTextField dateField;
	private JLabel lblStartTime;
	private JTextField startTimeField;
	private JLabel lblEndTime;
	private JTextField endTimeField;
	private JLabel lblTypeOfTreatment;
	private JTextField textTreatmentType;
	private JLabel lblCourseOfTreatment;
	private JTextField textTreatmentCourse;
	private JTextField textDentistForename;
	private JTextField textDentistSurname;

    /*
     * Checks if any of the text fields are empty
     */
    public boolean formFilled() {
    	if (forenameField.getText().trim().isEmpty() 
    			|| surnameField.getText().trim().isEmpty())
    		return false;
    	return true;
    }
    
	/**
	 * Create the frame.
	 */
	public NewAppointment() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPatientForename = new JLabel("Forename :");
		
		forenameField = new JTextField();
		forenameField.setColumns(10);
		
		lblDate = new JLabel("Date :");
		
		dateField = new JTextField();
		dateField.setColumns(10);
		
		lblStartTime = new JLabel("Start time : ");
		
		startTimeField = new JTextField();
		startTimeField.setColumns(10);
		
		lblEndTime = new JLabel("End time : ");
		
		endTimeField = new JTextField();
		endTimeField.setColumns(10);
		
		lblTypeOfTreatment = new JLabel("Type of Treatment : ");
		
		textTreatmentType = new JTextField();
		textTreatmentType.setColumns(10);
		
		lblCourseOfTreatment = new JLabel("Course of Treatment : ");
		
		textTreatmentCourse = new JTextField();
		textTreatmentCourse.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname : ");
		
		surnameField = new JTextField();
		surnameField.setColumns(10);
		
		JLabel lblForename = new JLabel("Forename : ");
		
		textDentistForename = new JTextField();
		textDentistForename.setColumns(10);
		
		JLabel lblDentistSurname = new JLabel("Surname : ");
		
		textDentistSurname = new JTextField();
		textDentistSurname.setColumns(10);
		
		JLabel lblDetailsOfAttending = new JLabel("Details of Attending Physician : ");
		
		JButton btnSubmit = new JButton("Submit");
/*		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formFilled()) {
					if (updateDB())
						JOptionPane.showMessageDialog(new JFrame(), "Registration Success");
					dispose();
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.", "Submission Error",
				            JOptionPane.ERROR_MESSAGE);
			}
		});*/
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblPatientForename)
										.addComponent(lblDate)
										.addComponent(lblStartTime))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(forenameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblSurname)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(surnameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(startTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblEndTime)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(endTimeField))
										.addComponent(dateField)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTypeOfTreatment)
										.addComponent(lblCourseOfTreatment))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(textTreatmentCourse, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
										.addComponent(textTreatmentType, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))))
							.addGap(62))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDetailsOfAttending)
							.addContainerGap(348, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblForename)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textDentistForename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblDentistSurname))
								.addComponent(btnBack))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSubmit)
								.addComponent(textDentistSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPatientForename)
						.addComponent(forenameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSurname)
						.addComponent(surnameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDate)
						.addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartTime)
						.addComponent(startTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndTime)
						.addComponent(endTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypeOfTreatment)
						.addComponent(textTreatmentType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCourseOfTreatment)
						.addComponent(textTreatmentCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addComponent(lblDetailsOfAttending)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblForename)
						.addComponent(textDentistForename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDentistSurname)
						.addComponent(textDentistSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSubmit)
						.addComponent(btnBack))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}