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
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

public class SecretaryCalendar extends JFrame {

    /**
     * Creates new form SecretaryCalendar
     */
    public SecretaryCalendar() {
    	try(Connection connection = DBConnect.getConnection(false)){
    		this.partners = Partner.getAll(connection);
    		this.dentist = partners.get(0);
    		this.hygienist = partners.get(1);
    		this.dentistAppointments = dentist.getWeekAppointments(connection, DateUtilities.thisWeek());
    		this.hygienistAppointments = hygienist.getWeekAppointments(connection, DateUtilities.thisWeek());
    	} catch(SQLException e){
    		DBConnect.printSQLError(e);
    	}

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    @SuppressWarnings("unchecked")
    // initialise components
    private void initComponents() {

        secretaryCalendarTabPane = new JTabbedPane();
        dentistTabPanel = new JPanel();
        dentistCalendarListPane = new JScrollPane();
        dentistCalendarList = new JList<>();
        dentistAddAppButton = new JButton();
        dentistDeleteAppButton = new JButton();
        hygienistTabPanel = new JPanel();
        hygienistCalendarListPane = new JScrollPane();
        hygienistCalendarList = new JList<>();
        hygienistAddAppButton = new JButton();
        hygienistDeleteAppButton = new JButton();

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
        dentistAddAppButton.setText("Add appointment");
        dentistAddAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FindPatient();
			}
		});
        
        dentistDeleteAppButton.setText("Delete appointment");
        dentistDeleteAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					selectedAppointmentDentist.delete(connection);
				}catch(SQLException ex){
		    		DBConnect.printSQLError(ex);
		    	}catch(DeleteForeignKeyException ex){
		    		System.err.println(ex.getTable());
		    	}
			}
		});

        
        hygienistAddAppButton.setText("Add appointment");
        hygienistAddAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FindPatient();
				// redirects to FindPatient where they can choose patient the appointment is for
			}
		});
        
        hygienistDeleteAppButton.setText("Delete appointment");
        hygienistDeleteAppButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try(Connection connection = DBConnect.getConnection(true)){
					selectedAppointmentHygienist.delete(connection);
				}catch(SQLException ex){
		    		DBConnect.printSQLError(ex);
		    	}catch(DeleteForeignKeyException ex){
		    		System.err.println(ex.getTable());
		    	}
			}
		});

        // Generated code - do not modify
        GroupLayout dentistTabPanelLayout = new GroupLayout(dentistTabPanel);
        dentistTabPanel.setLayout(dentistTabPanelLayout);
        dentistTabPanelLayout.setHorizontalGroup(
            dentistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(dentistTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dentistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(dentistCalendarListPane)
                    .addGroup(dentistTabPanelLayout.createSequentialGroup()
                        .addComponent(dentistAddAppButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dentistDeleteAppButton)
                        .addGap(0, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dentistTabPanelLayout.setVerticalGroup(
            dentistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(dentistTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dentistCalendarListPane, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dentistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dentistAddAppButton)
                    .addComponent(dentistDeleteAppButton))
                .addContainerGap())
        );

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
        hygienistTabPanel.setLayout(hygienistTabPanelLayout);
        hygienistTabPanelLayout.setHorizontalGroup(
            hygienistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(hygienistTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(hygienistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(hygienistCalendarListPane)
                    .addGroup(hygienistTabPanelLayout.createSequentialGroup()
                        .addComponent(hygienistAddAppButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hygienistDeleteAppButton)
                        .addGap(0, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
        hygienistTabPanelLayout.setVerticalGroup(
            hygienistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(hygienistTabPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hygienistCalendarListPane, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(hygienistTabPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(hygienistAddAppButton)
                    .addComponent(hygienistDeleteAppButton))
                .addContainerGap())
        );

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
}