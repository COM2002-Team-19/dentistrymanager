package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class DentistryCalendar extends JFrame {

	private JPanel contentPane;
	private ArrayList<Partner> partners;
	private ArrayList<Appointment> nextPatients;
	private Partner dentist;

	public static void main(String[] args) {
		
		new DentistryCalendar();
	}

	public DentistryCalendar() {
		
		try(Connection connection = DBConnect.getConnection(false)){
			this.partners = Partner.getAll(connection);
    		this.dentist = partners.get(0);
    		this.nextPatients = dentist.getDaysAppointments(connection);
    		String sql = "SELECT * FROM Appointment WHERE partner =  AND date = "+ DateUtilities.today() + ";";
ResultSet res = stmt.executeQuery(sql);
    		
    	}
    	catch(SQLException e){
    		DBConnect.printSQLError(e);
    	}
				
		// Current appointments on the left
		JPanel currentAppointment = new JPanel();
		currentAppointment.setLayout(new BorderLayout());
		// Title at the top
		JLabel currentAppTitle = new JLabel("Current Appointment:");
		// Text area
		JTextArea currentAppDisplay = new JTextArea();
		JScrollPane scrollPaneCurrent = new JScrollPane(currentAppDisplay);
		//Adding to display
		currentAppDisplay.setEditable(false);
		currentAppointment.add(currentAppTitle, BorderLayout.NORTH);
		currentAppointment.add(scrollPaneCurrent, BorderLayout.CENTER);
		JPanel currentButtons = new JPanel();
		// Buttons at the bottom
		currentButtons.setLayout(new GridLayout(1,0));
		JButton addTreatment = new JButton("Add Treatment");
		JButton finishCurrent = new JButton("Finish Appointment");
		currentButtons.add(addTreatment);
		currentButtons.add(finishCurrent);
		currentAppointment.add(currentButtons, BorderLayout.SOUTH);
		
		// Next appointments on the right
		JPanel nextAppointment = new JPanel();
		nextAppointment.setLayout(new BorderLayout());
		JLabel nextAppTitle = new JLabel("Next Appointments:");
		// NOT FINISHED
		JScrollPane scrollPaneNext = new JScrollPane(nextAppDisplay);
		nextAppointment.add(nextAppTitle, BorderLayout.NORTH);
		nextAppointment.add(scrollPaneNext, BorderLayout.CENTER);
		JPanel nextButtons = new JPanel();
		nextButtons.setLayout(new GridLayout(1,0));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
   		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(0, 2));
		setContentPane(contentPane);
		contentPane.add(currentAppointment);
		contentPane.add(nextAppointment);
		
		setVisible(true);
	}
}
