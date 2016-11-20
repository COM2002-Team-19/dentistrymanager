package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class printReceipt extends JFrame {

	private JPanel contentPane;
	private int id;
	private double balance;
	private double coveredCost;

	public printReceipt(Patient patient) {
		
		balance = patient.getBalance();
		id = patient.getPatientID();
		try(Connection connection = DBConnect.getConnection(false)){
			this.coveredCost = CoveredTreatment.getCoveredCost(connection, id, typeOfTreatment)
		}catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		// Text Area
		JTextArea recieptTextArea = new JTextArea();
		
		// Button
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
