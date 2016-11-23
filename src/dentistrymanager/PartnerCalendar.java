package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PartnerCalendar extends JFrame {

	private JPanel contentPane;
	private ArrayList<Partner> partners;
	private ArrayList<Appointment> nextPatients;
	private Partner p;
	private Appointment presentAppointment;
	private JList<Appointment> nextAppResultsList;
	private JTextArea currentAppDisplay;
 
	public PartnerCalendar(int i) {
		
		try(Connection connection = DBConnect.getConnection(false)){
			this.partners = Partner.getAll(connection);
			this.p = partners.get(i);
			this.nextPatients = p.getDaysAppointments(connection);
			this.presentAppointment = p.getNextAppointment(connection);
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
		currentAppDisplay = new JTextArea();
		
		updateValues();
		nextAppResultsList = new JList<Appointment>();
		updateAppResultList();
		nextAppResultsList.setCellRenderer(new AppointmentListRenderer());
		JScrollPane nextAppResults = new JScrollPane(nextAppResultsList);
			
		JScrollPane scrollPaneCurrent = new JScrollPane(currentAppDisplay);
		
		//Adding to display
		currentAppDisplay.setEditable(false);
		currentAppointment.add(currentAppTitle, BorderLayout.NORTH);
		currentAppointment.add(scrollPaneCurrent, BorderLayout.CENTER);
		JPanel currentButtons = new JPanel();
		
		// Buttons at the bottom
		currentButtons.setLayout(new GridLayout(1,0));
		JButton manageTreatmentButton = new JButton("Manage Treatments");
		manageTreatmentButton.setPreferredSize(new Dimension(100, 100));
		manageTreatmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(presentAppointment != null)
					new ManageTreatment(presentAppointment);
			}
		});
		
		JButton finishCurrentButton = new JButton("Finish Appointment");
		finishCurrentButton.setPreferredSize(new Dimension(100, 100));
		finishCurrentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					if(presentAppointment != null) {
						presentAppointment.finish(connection);
						
						// Get the cost of the appointment
						double cost = presentAppointment.getTotalCost(connection);
						presentAppointment.getPatient().updateBalance(connection, cost);
						CoveredTreatment.updateCoveredTreatments(connection, presentAppointment.getAppointmentID(), 
																	presentAppointment.getPatient().getPatientID());
						presentAppointment = p.getNextAppointment(connection);
						updateValues();
						updateAppResultList();
					}
				}
		    	catch(SQLException ex){
		    		DBConnect.printSQLError(ex);
		    	}
				
			}
		});

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SplashScreen();
				dispose();
			}
		});
		
		currentButtons.add(backButton);
		currentButtons.add(manageTreatmentButton);
		currentButtons.add(finishCurrentButton);

		currentAppointment.add(currentButtons, BorderLayout.SOUTH);
		
		// Next appointments on the right
		JPanel nextAppointment = new JPanel();
		nextAppointment.setLayout(new BorderLayout());
		JLabel nextAppTitle = new JLabel("Next Appointments:");
				
		JScrollPane scrollPaneNext = new JScrollPane(nextAppResults);
		nextAppointment.add(nextAppTitle, BorderLayout.NORTH);
		nextAppointment.add(scrollPaneNext, BorderLayout.CENTER);
		JPanel nextButtons = new JPanel();
		nextButtons.setLayout(new GridLayout(1,0));
		
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1100, 300);
		contentPane = new JPanel();
   		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(0, 2));
 		setContentPane(contentPane);
		contentPane.add(currentAppointment);
		contentPane.add(nextAppointment);

 		setVisible(true);
 	}
	
	private void updateValues(){

		if (presentAppointment != null ){
			String dateLabel = presentAppointment.getDate().toString();
			String forenameLabel = presentAppointment.getPatient().getForename();
			String surnameLabel = presentAppointment.getPatient().getSurname();
			Time startTimeLabel = presentAppointment.getStartTime();
			Time endTimeLabel = presentAppointment.getEndTime();
			String typeOfTreatmentLabel = presentAppointment.getTypeOfTreatment();

			String courseOfTreatment = "False";
			if (presentAppointment.getCourseOfTreatment()>0){courseOfTreatment = "True";}
			
			currentAppDisplay.setText("");
			currentAppDisplay.append("Date : "+dateLabel+"\n");
			currentAppDisplay.append("First Name : "+forenameLabel+"\n");
			currentAppDisplay.append("Surname : "+surnameLabel+"\n");
			currentAppDisplay.append("Start time : "+startTimeLabel+"\n");
			currentAppDisplay.append("End time : "+endTimeLabel+"\n");
			currentAppDisplay.append("Course of Treatment : "+courseOfTreatment+"\n");
			currentAppDisplay.append("Type of treatment : "+typeOfTreatmentLabel+"\n");
		}
		
		else
			currentAppDisplay.setText("All your appointments are done for the day.");
	}
	
	private void updateAppResultList() {
    	DefaultListModel<Appointment> model = new DefaultListModel<>();
    	if (!nextPatients.isEmpty())
    		nextPatients.remove(0);
    	for(int i=0; i<nextPatients.size();i++)
    		model.addElement(nextPatients.get(i));
    	nextAppResultsList.setModel(model);
    }
 }