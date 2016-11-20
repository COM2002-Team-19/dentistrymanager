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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RegisterTreatment extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> treatmentCombo;
	private String[] treatmentStr;
	
	public static void main(String[] args){
		new RegisterTreatment();
	}
	
    public boolean formFilled() {
    	if ()
    		return false;
    	return true;
    }
    
    public boolean updateDB() {
		boolean success = false;
		
		try(Connection connection = DBConnect.getConnection(true)){
			long date = Long.valueOf(yearCombo.getSelectedItem().toString() + monthCombo.getSelectedItem().toString() + dayCombo.getSelectedItem().toString());
			String partner = partnerCombo.getSelectedItem().toString();
			int startTime = Integer.valueOf(startTimeField.getText());
			int endTime = Integer.valueOf(endTimeField.getText());
			String typeOfT = typeOfTreatmentCombo.getSelectedItem().toString();
			int courseOfT = 0;//getCourseOfTreatment(); #TODO
			Appointment app = new Appointment(partner, date, startTime, endTime, patient, typeOfT, courseOfT);
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		return success;
    }

	public RegisterTreatment() {
		
		// Combo section
//		JPanel comboPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		treatmentCombo = new JComboBox<String>();
		try(Connection con = DBConnect.getConnection(false)){
			ArrayList<Treatment> treatments = Treatment.getAll(con);
			treatmentStr = new String[treatments.size()];
			for (Treatment tr : treatments)
				treatmentStr[treatments.indexOf(tr)] = (tr.toString());
		} catch (SQLException e){
			DBConnect.printSQLError(e);
		}
        treatmentCombo.setModel(new DefaultComboBoxModel<String>(treatmentStr));
        
        
        // Type of treatment section
        JPanel treatmentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel treatmentLabel = new JLabel("Type of treatment : ");
        JTextField treatmentField = new JTextField(10);
        treatmentPane.add(treatmentLabel);
        treatmentPane.add(treatmentField);
        
        // Cost section
        JPanel costPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel costlabel = new JLabel("Cost : ");
        JTextField costField = new JTextField(10);
        costPane.add(costlabel);
        costPane.add(costField);
        
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
		
		setVisible(true);
		pack();
	}

}
