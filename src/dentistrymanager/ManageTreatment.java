package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
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
import javax.swing.JTextField;
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
	private int appointmentID;
	private int patientID;
	private Patient patient;
	private ArrayList<TreatmentRecord> appointmentTreatments;
	
    public boolean formFilled() {
    	if (treatmentField.getText().isEmpty())
    		return false;
    	return true;
    }
    
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
				System.out.println(rec.toString());
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
		
		this.patient = appointment.getPatient();
		this.patientID = appointment.getPatient().getPatientID();
		this.appointmentID = appointment.getAppointmentID();
		
		try(Connection con = DBConnect.getConnection(false)){
			this.allTreatments = Treatment.getAll(con);
			this.appointmentTreatments = TreatmentRecord.getAll(con, appointmentID);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		// Treatment record list
		JPanel treatmentRecordPanel = new JPanel(new BorderLayout());
		treatmentRecordList = new JList<TreatmentRecord>();
		treatmentRecordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treatmentRecordList.setCellRenderer(new TreatmentRecordListRenderer());
		treatmentRecordList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent event) {
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
        JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formFilled()) {
					if (updateDB(1))
						JOptionPane.showMessageDialog(new JFrame(), "Treatment Added");
					else
					    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in adding this treatment. Please try again.",
					    		"Submission Error", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				else
				    JOptionPane.showMessageDialog(new JFrame(), "Please fill in all fields.", "Submission Error",
				            JOptionPane.ERROR_MESSAGE);
			}
		});
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if (updateDB(-1))
						JOptionPane.showMessageDialog(new JFrame(), "Treatment Deleted");
					else
					    JOptionPane.showMessageDialog(new JFrame(), "There has been an error in deleting this treatment. Please try again.",
					    		"Submission Error", JOptionPane.ERROR_MESSAGE);
					dispose();
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
				selectedTreatment = (Treatment)treatmentCombo.getSelectedItem();
		        treatmentField.setText(selectedTreatment.getTypeOfTreatment());
		        costField.setText(Double.toString(selectedTreatment.getCost()));
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
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
	
    private void updateTreatmentRecordList() {
    	DefaultListModel<TreatmentRecord> model = new DefaultListModel<>();
    	for(TreatmentRecord treatmentRecord: appointmentTreatments)
    		model.addElement(treatmentRecord);
    	treatmentRecordList.setModel(model);
    }

}
