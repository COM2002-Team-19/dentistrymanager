package dentistrymanager;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JList;
/*
 * For a given patient in FindPatient, be able to view the appointments they have booked, 
 * and the appointments a partner has got booked so you can find a time that is free for
 * the patient and the partner.
 */

public class FindAppointment extends JFrame {
	
	//variables list
	private JPanel contentPane;
	private JComboBox<String> comboPartner = new JComboBox<String>();
//  private String selectedPartner;
//  private String[] typesStr;
    private String[] partnersStr;
//    private JComboBox<String> typeOfTreatmentCombo;
    private JTextField patientNameField;
    private Patient patient;
	
	/**
	 * Create the frame.
	 */
	public FindAppointment(Patient p) {
		setResizable(false);
		
		//initialise
		//selectedPartner = "";
		patient = p;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 290, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Select Partner Combo
		JLabel lblPartner = new JLabel("Partner to see : ");
		comboPartner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				selectedPartner = (String)comboPartner.getSelectedItem();
//				updateTreatmentList();
			}
		});
		updatePartnerList();
		
		//Auto-generated code
		@SuppressWarnings("rawtypes")
		JList list = new JList();
		
		JLabel lblPatientName = new JLabel("Patient name : ");
		
		patientNameField = new JTextField();
		patientNameField.setEditable(false);
		patientNameField.setColumns(10);
		if (patient!=null)
			patientNameField.setText(patient.getForename()+" "+patient.getSurname());		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(list, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(lblPatientName)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(patientNameField))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(lblPartner)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboPartner, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPartner)
						.addComponent(comboPartner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPatientName)
						.addComponent(patientNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
					.addComponent(list, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
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
		comboPartner.setModel(new DefaultComboBoxModel<String>(partnersStr));
	}
	
//	code that listens to the other box
//	private void updateTreatmentList() {
//		try(Connection con = DBConnect.getConnection(false)){
//			ArrayList<TypeOfTreatment> types = TypeOfTreatment.getAllByPartner(con, selectedPartner);
//			typesStr = new String[types.size()];
//			for (TypeOfTreatment tot : types)
//				typesStr[types.indexOf(tot)] = (tot.toString());
//
//		} catch (SQLException e){
//			DBConnect.printSQLError(e);
//		}
//		
//		typeOfTreatmentCombo.setModel(new DefaultComboBoxModel<String>(typesStr));
//	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Patient patient = new Patient();
					new FindAppointment(patient);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}