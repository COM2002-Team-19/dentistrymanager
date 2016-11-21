package dentistrymanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SecretaryCalendar extends JFrame {

    /**
     * Creates new form SecretaryCalendar
     */
    public SecretaryCalendar() {
    	getData();
        initComponents();
		this.setTitle("Calendar");
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    // initialise components
    private void initComponents() {

        secretaryCalendarTabPane = new JTabbedPane();
        dentistTabPanel = new JPanel();
        dentistCalendarListPane = new JScrollPane();
        dentistCalendarList = new JList<>();
        dentistAddAppButton = new JButton("Add Appointment");
        dentistDeleteAppButton = new JButton("Delete Appointment");
        hygienistTabPanel = new JPanel();
        hygienistCalendarListPane = new JScrollPane();
        hygienistCalendarList = new JList<>();
        hygienistAddAppButton = new JButton("Add Appointment");
        hygienistDeleteAppButton = new JButton("Delete Appointment");
        dentistUnavailableButton = new JButton("Mark Unavailability");
        hygienistUnavailableButton = new JButton("Mark Unavailability");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        dentistCalendarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dentistCalendarList.setCellRenderer(new AppointmentListRenderer());
        updateDentistList();
        dentistCalendarList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent event) {
        		int selectedIndex = dentistCalendarList.getSelectedIndex();
        		if(selectedIndex != -1) {
        			selectedAppointmentDentist = dentistCalendarList.getSelectedValue();
        			
        		}
        	}
        });
        dentistCalendarListPane.setViewportView(dentistCalendarList);

        // Buttons
        dentistAddAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FindPatient();
				updateDentistList();
    			getData();
				// redirects to FindPatient where they can choose patient the appointment is for
			}
		});
        
        dentistDeleteAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					selectedAppointmentDentist.delete(connection);
					updateDentistList();
        			getData();
				}catch(SQLException ex){
		    		DBConnect.printSQLError(ex);
		    	}catch(DeleteForeignKeyException ex){
		    		System.err.println(ex.getTable());
		    	}
			}
		});
        
        dentistUnavailableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewAppointment();
			}
		});

        
        hygienistAddAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FindPatient();
				updateHygienistList();
				getData();
				// redirects to FindPatient where they can choose patient the appointment is for
			}
		});
        
        hygienistDeleteAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					selectedAppointmentHygienist.delete(connection);
					updateHygienistList();
        			getData();
				}catch(SQLException ex){
		    		DBConnect.printSQLError(ex);
		    	}catch(DeleteForeignKeyException ex){
		    		System.err.println(ex.getTable());
		    	}
			}
		});
        
        hygienistUnavailableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewAppointment();
			}
		});

        // Generated code - do not modify
        GroupLayout dentistTabPanelLayout = new GroupLayout(dentistTabPanel);
        dentistTabPanelLayout.setHorizontalGroup(
        	dentistTabPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(dentistTabPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(dentistTabPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(dentistCalendarListPane, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        				.addGroup(dentistTabPanelLayout.createSequentialGroup()
        					.addComponent(dentistAddAppButton)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(dentistDeleteAppButton)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(dentistUnavailableButton)))
        			.addContainerGap())
        );
        dentistTabPanelLayout.setVerticalGroup(
        	dentistTabPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, dentistTabPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(dentistCalendarListPane, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(dentistTabPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(dentistAddAppButton)
        				.addComponent(dentistDeleteAppButton)
        				.addComponent(dentistUnavailableButton))
        			.addContainerGap())
        );
        dentistTabPanel.setLayout(dentistTabPanelLayout);

        secretaryCalendarTabPane.addTab("Dentist", dentistTabPanel);

        hygienistCalendarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        hygienistCalendarList.setCellRenderer(new AppointmentListRenderer());
        updateHygienistList();
        hygienistCalendarList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent event) {
        		int selectedIndex = hygienistCalendarList.getSelectedIndex();
        		if(selectedIndex != -1) {
        			selectedAppointmentHygienist = hygienistCalendarList.getSelectedValue();

        		}
        	}
        });
        hygienistCalendarListPane.setViewportView(hygienistCalendarList);

        GroupLayout hygienistTabPanelLayout = new GroupLayout(hygienistTabPanel);
        hygienistTabPanelLayout.setHorizontalGroup(
        	hygienistTabPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(hygienistTabPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(hygienistTabPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(hygienistCalendarListPane, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
        				.addGroup(hygienistTabPanelLayout.createSequentialGroup()
        					.addComponent(hygienistAddAppButton)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(hygienistDeleteAppButton)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(hygienistUnavailableButton)))
        			.addContainerGap())
        );
        hygienistTabPanelLayout.setVerticalGroup(
        	hygienistTabPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, hygienistTabPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(hygienistCalendarListPane, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(hygienistTabPanelLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(hygienistAddAppButton)
        				.addComponent(hygienistDeleteAppButton)
        				.addComponent(hygienistUnavailableButton))
        			.addContainerGap())
        );
        hygienistTabPanel.setLayout(hygienistTabPanelLayout);

        secretaryCalendarTabPane.addTab("Hygienist", hygienistTabPanel);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(secretaryCalendarTabPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(secretaryCalendarTabPane)
                .addContainerGap())
        );

        pack();
        setVisible(true);
    }
    
    private void updateDentistList() {
    	DefaultListModel<Appointment> model = new DefaultListModel<>();
    	for(Appointment appointment: dentistAppointments)
    		model.addElement(appointment);
    	dentistCalendarList.setModel(model);
    }
    
    private void updateHygienistList() {
    	DefaultListModel<Appointment> model = new DefaultListModel<>();
    	for(Appointment appointment: hygienistAppointments)
    		model.addElement(appointment);
    	hygienistCalendarList.setModel(model);
    }
    
    public void getData(){
    	try(Connection connection = DBConnect.getConnection(false)){
    		this.partners = Partner.getAll(connection);
    		this.dentist = partners.get(0);
    		this.hygienist = partners.get(1);
    		this.dentistAppointments = dentist.getWeekAppointments(connection, DateTimeUtilities.thisWeek());
    		this.hygienistAppointments = hygienist.getWeekAppointments(connection, DateTimeUtilities.thisWeek());
    	} catch(SQLException e){
    		DBConnect.printSQLError(e);
    	}
    }
    
    // System variables
    private ArrayList<Partner> partners;
    private Partner dentist;
    private Partner hygienist;
    private ArrayList<Appointment> dentistAppointments;
    private ArrayList<Appointment> hygienistAppointments;
    private Appointment selectedAppointmentDentist;
    private Appointment selectedAppointmentHygienist;

    // Variables declaration - do not modify
    private JButton dentistAddAppButton;
    private JList<Appointment> dentistCalendarList;
    private JScrollPane dentistCalendarListPane;
    private JButton dentistDeleteAppButton;
    private JPanel dentistTabPanel;
    private JButton hygienistAddAppButton;
    private JList<Appointment> hygienistCalendarList;
    private JScrollPane hygienistCalendarListPane;
    private JButton hygienistDeleteAppButton;
    private JPanel hygienistTabPanel;
    private JTabbedPane secretaryCalendarTabPane;
    private JButton hygienistUnavailableButton;
    private JButton dentistUnavailableButton;
}