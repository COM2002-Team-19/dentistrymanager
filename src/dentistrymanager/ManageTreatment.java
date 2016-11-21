package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class ManageTreatment extends JFrame {

	private JPanel mainPane;
	private JPanel contentPane;
	private JComboBox<Treatment> treatmentCombo;
	private JList<TreatmentRecord> treatmentRecordList;
	private Treatment selectedTreatment;
	private TreatmentRecord selectedTreatmentRecord;
	private ArrayList<Treatment> allTreatments;
	private JTextArea treatmentField;
	private JTextArea costField;
	private JPanel buttonPane;
	private JButton btnSubmit;
	private JButton btnDelete;
	private int appointmentID;
	private int patientID;
	private ArrayList<TreatmentRecord> appointmentTreatments;
    
    public boolean updateDB(int mode) {
		boolean success = true;
		
		String n = "";
		Double oc = 0.0;
		Double cc = 0.0;
		
		if(mode == 1) {
			n = selectedTreatment.getName();
			oc = selectedTreatment.getCost();
			cc = 0.0;
		}
		
		try(Connection connection = DBConnect.getConnection(false)){
			cc = CoveredTreatment.getCoveredCost(connection, patientID, n);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
			success = false;
		}
		
		try(Connection connection = DBConnect.getConnection(true)){
			
			if(mode==1){
				TreatmentRecord rec = new TreatmentRecord(appointmentID,n,oc,cc);
				rec.add(connection);
			}
			if(mode==-1){
				selectedTreatmentRecord.delete(connection);
			}
			
		} catch (SQLException e){
			DBConnect.printSQLError(e);
			success = false;
		} catch(DuplicateKeyException e){
			JOptionPane.showMessageDialog(new JFrame(), "Error", "Submission Error",
		            JOptionPane.ERROR_MESSAGE);
			success = false;
		} catch(DeleteForeignKeyException e){
			e.printStackTrace();
			success = false;
		}
		
		return success;
    }

	public ManageTreatment(Appointment appointment) {
		this.setTitle("Manage Treatments");
		
		this.patientID = appointment.getPatient().getPatientID();
		this.appointmentID = appointment.getAppointmentID();
		
		getData();
		
		// Treatment record list
		JPanel treatmentRecordPanel = new JPanel(new BorderLayout());
		treatmentRecordList = new JList<TreatmentRecord>();
		treatmentRecordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treatmentRecordList.setCellRenderer(new TreatmentRecordListRenderer());
		treatmentRecordList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent event) {
        		btnDelete.setEnabled(true);
        		int selectedIndex = treatmentRecordList.getSelectedIndex();
        		if(selectedIndex != -1) {
        			selectedTreatmentRecord = treatmentRecordList.getSelectedValue();
        		}
        	}
        });
		updateTreatmentRecordList();
		
		treatmentRecordPanel.add(treatmentRecordList);
        
        // Type of treatment section
        JPanel treatmentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel treatmentLabel = new JLabel("Type of treatment : ");
        treatmentField = new JTextArea(1,10);

        treatmentField.setEditable(false);
        treatmentPane.add(treatmentLabel);
        treatmentPane.add(treatmentField);
        
        // Cost section
        JPanel costPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel costlabel = new JLabel("Cost : ");
        costField = new JTextArea(1,10);
        costField.setEditable(false);
        costPane.add(costlabel);
        costPane.add(costField);
        
        // Submit button
        buttonPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (updateDB(1)) {
					getData();
					updateTreatmentRecordList();
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in adding this treatment. Please try again.",
				    		"Submission Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (updateDB(-1)) {
					getData();
					updateTreatmentRecordList();
					if (appointmentTreatments.isEmpty())
						btnDelete.setEnabled(false);
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in deleting this treatment. Please try again.",
				    		"Submission Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		buttonPane.add(btnSubmit);
		buttonPane.add(btnDelete);
		
		treatmentCombo = new JComboBox<Treatment>();
		treatmentCombo.setRenderer(new TreatmentListRenderer());
    	DefaultComboBoxModel<Treatment> model = new DefaultComboBoxModel<>();    	
    	for(Treatment treatment: allTreatments)
    		model.addElement(treatment);
    	treatmentCombo.setModel(model);
        treatmentCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTreatmentFields();
			}
		});
        
		updateTreatmentFields();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
		
		// adding elements
		mainPane.add(treatmentCombo);
		mainPane.add(treatmentPane);
		mainPane.add(costPane);
		mainPane.add(buttonPane);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5 ,5, 5, 5));
		contentPane.setLayout(new GridLayout(0,2));
		setContentPane(contentPane);
		contentPane.add(treatmentRecordPanel);
		contentPane.add(mainPane);
		
		setVisible(true);
		pack();
	}
	
	private void getData() {
		try(Connection con = DBConnect.getConnection(false)){
			this.allTreatments = Treatment.getAll(con);
			this.appointmentTreatments = TreatmentRecord.getAll(con, appointmentID);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
	}
	
	private void updateTreatmentFields() {
		selectedTreatment = (Treatment)treatmentCombo.getSelectedItem();
        treatmentField.setText(selectedTreatment.getTypeOfTreatment());
		DecimalFormat twoDecimals = new DecimalFormat("#0.00");
        costField.setText(twoDecimals.format(selectedTreatment.getCost()));
	}
	
    private void updateTreatmentRecordList() {
    	DefaultListModel<TreatmentRecord> model = new DefaultListModel<>();
    	for(TreatmentRecord treatmentRecord: appointmentTreatments)
    		model.addElement(treatmentRecord);
    	treatmentRecordList.setModel(model);
    }
}