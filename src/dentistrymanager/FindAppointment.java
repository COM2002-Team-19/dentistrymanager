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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
/*
 * For a given patient in FindPatient, be able to view the appointments they have booked, 
 * and the appointments a partner has got booked so you can find a time that is free for
 * the patient and the partner.
 */

public class FindAppointment extends JFrame {
	
	//variables list
	private JPanel contentPane;
	private JComboBox<Partner> comboPartner = new JComboBox<Partner>();
    private Partner selectedPartner;
    private String[] partnersStr;
    private JTextField patientNameField;
    private JList<Appointment> resultsList;
    private JScrollPane resultsPane = new JScrollPane();
    private ArrayList<Appointment> resultAppointments;
    private Appointment selectedAppointmentResult;
    private ArrayList<Partner> partners;
    private Partner dentist;
    private Partner hygienist;
	
	/**
	 * Create the frame.
	 */
	public FindAppointment(Patient p) {
		setResizable(false);
		
		try(Connection connection = DBConnect.getConnection(false)){
    		this.partners = Partner.getAll(connection);
    		this.dentist = partners.get(0);
    		this.hygienist = partners.get(1);
    		this.resultAppointments = Appointment.findByPartnerPatient(connection, p.getForename(), dentist.getName());
    	} catch(SQLException e){
    		DBConnect.printSQLError(e);
    	}
		
		//initialise
		selectedPartner = partners.get(0);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 596, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Select Partner Combo
		JLabel lblPartner = new JLabel("Partner to see : ");
		comboPartner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedPartner = (Partner)comboPartner.getSelectedItem();
				try(Connection connection = DBConnect.getConnection(false)){
					resultAppointments = Appointment.findByPartnerPatient(connection, p.getForename(), selectedPartner.getName());
		    	} catch(SQLException e){
		    		DBConnect.printSQLError(e);
		    	}
				
//				updateTreatmentList();
			}
		});
		updatePartnerList();
		
		JLabel lblPatientName = new JLabel("Patient name : ");
		
		patientNameField = new JTextField();
		patientNameField.setEditable(false);
		patientNameField.setColumns(10);
		if (p!=null)
			patientNameField.setText(p.getForename()+" "+p.getSurname());		
		
		//Results list window updating.
		resultsList = new JList<Appointment>();
		resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resultsList.setCellRenderer(new AppointmentListRenderer());
		updateResultsList();
		resultsList.addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent event) {
		    int selectedIndex = resultsList.getSelectedIndex();
		    	if(selectedIndex != -1) {
					selectedAppointmentResult = resultsList.getSelectedValue();
		 		}
			}
		});
		resultsPane.setViewportView(resultsList);
		
		
		//AUTO-GENERATED Code
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(resultsPane, GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(lblPatientName)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(patientNameField, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))
							.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
								.addComponent(lblPartner)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboPartner, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap())
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
					.addGap(18)
					.addComponent(resultsPane, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
					.addContainerGap())
		);
		resultsPane.setViewportView(resultsList);
		contentPane.setLayout(gl_contentPane);
		setVisible(true);
	}
	
	//Gets the partners for the Combo Box
	private void updatePartnerList() {	
	    try(Connection con = DBConnect.getConnection(false)){
			ArrayList<Partner> partners = Partner.getAll(con);
			partnersStr = new String[partners.size()];
			for (Partner pa : partners)
				partnersStr[partners.indexOf(pa)] = (pa.toString());
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		comboPartner.setModel(new DefaultComboBoxModel<Partner>());
	}
	
	
	//Updates the ResultsList, the PartnerList(?)
	private void updateResultsList() {
    	DefaultListModel<Appointment> model = new DefaultListModel<>();
    	for(Appointment appointment: resultAppointments)
    		model.addElement(appointment);
    	resultsList.setModel(model);
    }
}