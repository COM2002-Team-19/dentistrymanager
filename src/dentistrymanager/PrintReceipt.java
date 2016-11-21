package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.Font;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


@SuppressWarnings("serial")
public class PrintReceipt extends JFrame {

	private JPanel contentPane;
	private int id;
	private double balance;
	private double coveredCost;
	private ArrayList<String> amountOwed;
	

	public PrintReceipt(Patient patient) {
		
		balance = patient.getBalance();
		id = patient.getPatientID();
		try(Connection connection = DBConnect.getConnection(false)){
			this.amountOwed = patient.getAmountOwed(connection);
		}catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		JPanel printoutArea = new JPanel(new BorderLayout());
		JTextArea recieptTextArea = new JTextArea();
		recieptTextArea.setEditable(false);
		String newline = "\n";
		recieptTextArea.append("Patient : "+patient.getForename()+" "+patient.getSurname()+newline);
		for(int i=0; i<amountOwed.size(); i++){
			recieptTextArea.append(amountOwed.get(i)+newline);
		}
		Font font2 = new Font("courier", Font.BOLD, 20);
		JTextField amountOwedField = new JTextField();
		amountOwedField.setFont(font2);
		amountOwedField.setHorizontalAlignment(JTextField.CENTER);
		amountOwedField.setEditable(false);
		amountOwedField.setText("Amount Owed : "+patient.getBalance());
		printoutArea.add(recieptTextArea, BorderLayout.CENTER);
		printoutArea.add(amountOwedField, BorderLayout.SOUTH);
		
		// Button
		JButton payAll = new JButton("Pay All");
		JButton close = new JButton("Close");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(printoutArea, BorderLayout.CENTER);
		contentPane.add(payAll, BorderLayout.SOUTH);
		pack();
		setVisible(true);
		
	}

}
