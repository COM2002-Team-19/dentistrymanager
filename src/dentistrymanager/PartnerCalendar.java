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
	private Appointment nextAppointment;
	private JList<Appointment> nextAppResultsList;
	private JTextArea currentAppDisplay;
 
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
		currentAppDisplay = new JTextArea();
		
		updateValues();
			
		JScrollPane scrollPaneCurrent = new JScrollPane(currentAppDisplay);
		
		//Adding to display
		currentAppDisplay.setEditable(false);
		currentAppointment.add(currentAppTitle, BorderLayout.NORTH);
		currentAppointment.add(scrollPaneCurrent, BorderLayout.CENTER);
		JPanel currentButtons = new JPanel();
		
		// Buttons at the bottom
		currentButtons.setLayout(new GridLayout(1,0));
		JButton manageTreatment = new JButton("Manage Treatments");
		manageTreatment.setPreferredSize(new Dimension(100, 100));
		manageTreatment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nextAppointment != null)
					new ManageTreatment(nextAppointment);
			}
		});
		
		JButton finishCurrent = new JButton("Finish Appointment");
		finishCurrent.setPreferredSize(new Dimension(100, 100));
		finishCurrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					if(nextAppointment != null) {
						nextAppointment.finish(connection);
						nextAppointment = p.getNextAppointment(connection);
						updateAppResultList();
						updateValues();
					}
				}
		    	catch(SQLException ex){
		    		DBConnect.printSQLError(ex);
		    	}
				
			}
		});
		
		currentButtons.add(manageTreatment);
		currentButtons.add(finishCurrent);
		currentAppointment.add(currentButtons, BorderLayout.SOUTH);
		
		// Next appointments on the right
		JPanel nextAppointment = new JPanel();
		nextAppointment.setLayout(new BorderLayout());
		JLabel nextAppTitle = new JLabel("Next Appointments:");
		
		// Insert JList
		nextAppResultsList = new JList<Appointment>();
		updateAppResultList();
		nextAppResultsList.setCellRenderer(new AppointmentListRenderer());
		JScrollPane nextAppResults = new JScrollPane(nextAppResultsList);

		
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
//		if (!this.nextPatients.isEmpty() ){
			String newline = "\n";
			String dateLabel = nextAppointment.getDate().toString();
			String forenameLabel = nextAppointment.getPatient().getForename();
			String surnameLabel = nextAppointment.getPatient().getSurname();
			Time startTimeLabel = nextAppointment.getStartTime();
			Time endTimeLabel = nextAppointment.getEndTime();
			String typeOfTreatmentLabel = nextAppointment.getTypeOfTreatment();
			String courseOfTreatment = "False";
			
			if (nextAppointment.getCourseOfTreatment()>0){courseOfTreatment = "True";}
			
			currentAppDisplay.setText("");
			currentAppDisplay.append("Date : "+dateLabel+newline);
			currentAppDisplay.append("First Name : "+forenameLabel+newline);
			currentAppDisplay.append("Surname : "+surnameLabel+newline);
			currentAppDisplay.append("Start time : "+startTimeLabel+newline);
			currentAppDisplay.append("End time : "+endTimeLabel+newline);
			currentAppDisplay.append("Course of Treatment : "+courseOfTreatment+newline);
			currentAppDisplay.append("Type of treatment : "+typeOfTreatmentLabel+newline);
//		}
	}
	
	private void updateAppResultList() {
    	DefaultListModel<Appointment> model = new DefaultListModel<>();
    	for(Appointment appointment: nextPatients)
    		model.addElement(appointment);
    	nextAppResultsList.setModel(model);
    }
 }