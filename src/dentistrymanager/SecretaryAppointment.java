package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class SecretaryAppointment extends JFrame {

	private JPanel contentPane;
	private JTextField textForename;
	private JLabel lblDate;
	private JTextField textDate;
	private JLabel lblStartTime;
	private JTextField textStartTime;
	private JLabel lblEndTime;
	private JTextField textEndTime;
	private JLabel lblTypeOfTreatment;
	private JTextField textTreatmentType;
	private JLabel lblCourseOfTreatment;
	private JTextField textTreatmentCourse;
	private JTextField textSurname;
	private JTextField textDentistForename;
	private JTextField textDentistSurname;

	/**
	 * Create the frame.
	 */
	public SecretaryAppointment() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblPatientForename = new JLabel("Forename :");
		
		textForename = new JTextField();
		textForename.setColumns(10);
		
		lblDate = new JLabel("Date :");
		
		textDate = new JTextField();
		textDate.setColumns(10);
		
		lblStartTime = new JLabel("Start time : ");
		
		textStartTime = new JTextField();
		textStartTime.setColumns(10);
		
		lblEndTime = new JLabel("End time : ");
		
		textEndTime = new JTextField();
		textEndTime.setColumns(10);
		
		lblTypeOfTreatment = new JLabel("Type of Treatment : ");
		
		textTreatmentType = new JTextField();
		textTreatmentType.setColumns(10);
		
		lblCourseOfTreatment = new JLabel("Course of Treatment : ");
		
		textTreatmentCourse = new JTextField();
		textTreatmentCourse.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname : ");
		
		textSurname = new JTextField();
		textSurname.setColumns(10);
		
		JLabel lblForename = new JLabel("Forename : ");
		
		textDentistForename = new JTextField();
		textDentistForename.setColumns(10);
		
		JLabel lblDentistSurname = new JLabel("Surname : ");
		
		textDentistSurname = new JTextField();
		textDentistSurname.setColumns(10);
		
		JLabel lblDetailsOfAttending = new JLabel("Details of Attending Physician : ");
		
		JButton btnSubmit = new JButton("Submit");
		
		JButton btnBack = new JButton("Back");
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
											.addComponent(textForename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblSurname)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(textStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblEndTime)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textEndTime))
										.addComponent(textDate)))
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
						.addComponent(textForename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSurname)
						.addComponent(textSurname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDate)
						.addComponent(textDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStartTime)
						.addComponent(textStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEndTime)
						.addComponent(textEndTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecretaryAppointment frame = new SecretaryAppointment();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}