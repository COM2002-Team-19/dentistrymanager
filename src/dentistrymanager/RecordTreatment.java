package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RecordTreatment extends JFrame {

	private JPanel contentPane;
	private JComboBox<Treatment> treatmentCombo;
	private String[] treatmentStr;
	private Treatment selectedTreatment;
	private ArrayList<Treatment> treatments;
	private JTextArea treatmentField;
	private JTextArea costField;
	private int appointmentID;
	
	
    public boolean formFilled() {
    	if (treatmentField.getText().isEmpty())
    		return false;
    	return true;
    }
    
    public boolean updateDB() {
		boolean success = false;
		
		try(Connection connection = DBConnect.getConnection(true)){
//			long date = Long.valueOf(yearCombo.getSelectedItem().toString() + monthCombo.getSelectedItem().toString() + dayCombo.getSelectedItem().toString());
//			String partner = partnerCombo.getSelectedItem().toString();
//			int startTime = Integer.valueOf(startTimeField.getText());
//			int endTime = Integer.valueOf(endTimeField.getText());
//			String typeOfT = typeOfTreatmentCombo.getSelectedItem().toString();
//			int courseOfT = 0;//getCourseOfTreatment(); #TODO
			
			String n = selectedTreatment.getName();
			Double oc = selectedTreatment.getCost();
			Double cc = 0.0;
			
			
			TreatmentRecord rec = new TreatmentRecord(appointmentID,n,oc,cc);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		return success;
    }

	public RecordTreatment(Appointment appointment) {
		
		this.appointmentID = appointment.getAppointmentID();
		try(Connection con = DBConnect.getConnection(false)){
			this.treatments = Treatment.getAll(con);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
        
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
		buttonPane.add(btnSubmit);
		
		treatmentCombo = new JComboBox<Treatment>();
		treatmentCombo.setRenderer(new TreatmentListRenderer());
    	DefaultComboBoxModel<Treatment> model = new DefaultComboBoxModel<>();
    	for(Treatment treatment: treatments)
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
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new FlowLayout());
//		contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		// adding elements
		contentPane.add(treatmentCombo);
		contentPane.add(treatmentPane);
		contentPane.add(costPane);
		contentPane.add(buttonPane);
		
		setVisible(true);
		pack();
	}

}
