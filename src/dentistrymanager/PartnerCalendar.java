package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PartnerCalendar extends JFrame {

	private JPanel contentPane;
	private ArrayList<Partner> partners;
	private ArrayList<Appointment> nextPatients;
	private Partner p;
	private Appointment nextAppointment;
 
	public PartnerCalendar(int i) {
		try(Connection connection = DBConnect.getConnection(false)){
			this.partners = Partner.getAll(connection);
			this.p = partners.get(i);
			this.nextPatients = p.getDaysAppointments(connection);
			this.nextAppointment = p.getNextAppointment(connection);
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
		if (!this.nextPatients.isEmpty()){
			String newline = "\n";
			String dateLabel = Long.toString(nextAppointment.getDate());
			String forenameLabel = nextAppointment.getPatient().getForename();
			String surnameLabel = nextAppointment.getPatient().getSurname();
			int startTimeLabel = nextAppointment.getStartTime();
			int endTimeLabel = nextAppointment.getEndTime();
			String typeOfTreatmentLabel = nextAppointment.getTypeOfTreatment();
			String courseOfTreatment = "False";
			if (nextAppointment.getCourseOfTreatment()>0){courseOfTreatment = "True";}
			currentAppDisplay.append("Date : "+dateLabel+newline);
			currentAppDisplay.append("First Name : "+forenameLabel+newline);
			currentAppDisplay.append("Surname : "+surnameLabel+newline);
			currentAppDisplay.append("Start time : "+startTimeLabel+newline);
			currentAppDisplay.append("End time : "+endTimeLabel+newline);
			currentAppDisplay.append("Type of treatment : "+typeOfTreatmentLabel+newline);
			currentAppDisplay.append("Course of treatment : "+courseOfTreatment+newline);
		}		
		JScrollPane scrollPaneCurrent = new JScrollPane(currentAppDisplay);
		
		//Adding to display
		currentAppDisplay.setEditable(false);
		currentAppointment.add(currentAppTitle, BorderLayout.NORTH);
		currentAppointment.add(scrollPaneCurrent, BorderLayout.CENTER);
		JPanel currentButtons = new JPanel();
		
		// Buttons at the bottom
		currentButtons.setLayout(new GridLayout(1,0));
		JButton addTreatment = new JButton("Add Treatment");
		addTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton delTreatment = new JButton("Delete Treatment");
		delTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton finishCurrent = new JButton("Finish Appointment");
		finishCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		currentButtons.add(addTreatment);
		currentButtons.add(delTreatment);
		currentButtons.add(finishCurrent);
		currentAppointment.add(currentButtons, BorderLayout.SOUTH);
		
		// Next appointments on the right
		JPanel nextAppointment = new JPanel();
		nextAppointment.setLayout(new BorderLayout());
		JLabel nextAppTitle = new JLabel("Next Appointments:");
		
		// Insert JList
		JList nextAppResultsList = new JList<Appointment>();
		nextAppResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nextAppResultsList.setCellRenderer(new AppointmentListRenderer());
		JScrollPane nextAppResults = new JScrollPane(nextAppResultsList);

		
		JScrollPane scrollPaneNext = new JScrollPane(nextAppResults);
		nextAppointment.add(nextAppTitle, BorderLayout.NORTH);
		nextAppointment.add(scrollPaneNext, BorderLayout.CENTER);
		JPanel nextButtons = new JPanel();
		nextButtons.setLayout(new GridLayout(1,0));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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