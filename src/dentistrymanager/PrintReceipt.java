package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
	private ArrayList<String> amountOwed;
	

	public PrintReceipt(Patient patient) {
		this.setTitle("Print Receipt");
		DecimalFormat moneyFormat = new DecimalFormat("#0.00");

		try(Connection connection = DBConnect.getConnection(false)){
			this.amountOwed = patient.getAmountOwed(connection);
		}catch (SQLException e){
			DBConnect.printSQLError(e);
		}
		
		JPanel printoutArea = new JPanel(new BorderLayout());
		JTextArea recieptTextArea = new JTextArea();
		recieptTextArea.setEditable(false);
		recieptTextArea.append("Patient : "+patient.getForename()+" "+patient.getSurname()+"\n");
		for(int i=0; i<amountOwed.size(); i++){
			recieptTextArea.append(amountOwed.get(i)+"\n");
		}
		Font font2 = new Font("courier", Font.BOLD, 20);
		JTextField amountOwedField = new JTextField();
		amountOwedField.setFont(font2);
		amountOwedField.setHorizontalAlignment(JTextField.CENTER);
		amountOwedField.setEditable(false);
		amountOwedField.setText("Amount Owed : "+moneyFormat.format(patient.getBalance()));
		printoutArea.add(recieptTextArea, BorderLayout.CENTER);
		printoutArea.add(amountOwedField, BorderLayout.SOUTH);
		
		// Button
		JButton payAllButton = new JButton("Pay All");
		payAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					patient.payAll(connection);
					dispose();
					new FindPatient();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new FindPatient();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 236, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		contentPane.add(printoutArea, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel(new GridLayout(0,1));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(payAllButton);
		buttonPanel.add(closeButton);
		pack();
		setVisible(true);	
	}
}