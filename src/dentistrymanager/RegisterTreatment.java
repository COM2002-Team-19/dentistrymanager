package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class RegisterTreatment extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> treatmentCombo;
	private String[] treatmentStr;
	
	public static void main(String[] args){
		new RegisterTreatment();
	}

	public RegisterTreatment() {
		
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
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new FlowLayout());
		setContentPane(contentPane);
		contentPane.add(treatmentCombo);
		
		setVisible(true);
		pack();
	}

}
