package dentistrymanager;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import java.sql.*;
import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class FindPatient extends JFrame {

    /**
     * Creates new form FindPatient
     */
    public FindPatient() {
    	getData("");
		selectedPatient = null;
		initComponents();
		this.setTitle("Find Patient");
    }

    /**
     * This method is called from within the constructor to initialise the form.
     */
    // initialise components
    private void initComponents() {
        
        searchField = new JTextField();
        searchButton = new JButton();
        searchButton.setText("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enteredName = searchField.getText();
				getData(enteredName);
				updatePatientList();
			}
		});
        
        searchResults = new JScrollPane();
        searchResultsList = new JList<>();
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.setCellRenderer(new PatientListRenderer());
        searchResultsList.addListSelectionListener(new ListSelectionListener() {
        	public void valueChanged(ListSelectionEvent event) {
        		int selectedIndex = searchResultsList.getSelectedIndex();
        		if(selectedIndex != -1) {
        			selectedPatient = searchResultsList.getSelectedValue();
        			loadSelectedPatientDetails();
        		}
        	}
        });
        
        // Loads the list of patients when the menu is entered
    	updatePatientList();
        
        patientDetails = new JPanel();
        
        nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        nameField.setEditable(false);
        
        addressLabel = new JLabel("Address:");
        addressField = new JScrollPane();
        addressArea = new JTextArea();
        addressArea.setEditable(false);
        
        phoneLabel = new JLabel("Phone number:");
        phoneField = new JTextField();
        phoneField.setEditable(false);
        
        healthcarePanel = new JPanel();
        healthcareLabel = new JLabel("Healthcare plan:");
        planNameArea = new JTextField();
        planNameArea.setEditable(false);
        
        subscribeButton = new JButton("Subscribe");
        subscribeButton.setEnabled(false);
        subscribeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!selectedPatient.hasHealthCarePlan())
					changePlan.setVisible(true);
				else
					try(Connection connection = DBConnect.getConnection(true)) {
						selectedPatient.unsubscribe(connection);
						refresh();
					} catch (SQLException ex) {
						DBConnect.printSQLError(ex);
					} catch (DeleteForeignKeyException ex) {
						System.out.println(ex.getTable());
					}
			}
		});

        changePlan = new JDialog();
        planComboBox = new JComboBox<>();
        planComboBox.setRenderer(new HealthcarePlanListRenderer());
        
        // Loads the plans in the combo box 
        updateHealthcarePlanList();
        
        updatePlan = new JButton("Update Plan");
        updatePlan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HealthcarePlan selectedPlan = (HealthcarePlan)planComboBox.getSelectedItem();
        		try(Connection connection = DBConnect.getConnection(true)) {
        			selectedPatient.subscribe(connection, selectedPlan);
        			changePlan.dispose();
        			refresh();
        		} catch(SQLException ex) {
        			DBConnect.printSQLError(ex);
        		} catch(DuplicateKeyException ex) {
        			System.out.println(ex.getTable());
        		}
        	}
        });
        
        owedPanel = new JPanel();
        owedLabel = new JLabel("Owed:");
        owedField = new JTextField();
        owedField.setEditable(false);
        owedField.setColumns(10);
        
        receiptButton = new JButton("Receipt"); 
        receiptButton.setEnabled(false);
        receiptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	new PrintReceipt(selectedPatient);
            }
        });
        
        deleteButton = new JButton("Delete Patient");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		try(Connection connection = DBConnect.getConnection(true)) {
        			selectedPatient.delete(connection);
        			refresh();
        		} catch (SQLException ex) {
        			DBConnect.printSQLError(ex);
        		} catch (DeleteForeignKeyException ex) {
				    JOptionPane.showMessageDialog(new JFrame(), "This Patient currently has appointments and so can't be deleted",
				    		"Patient Delete Error", JOptionPane.ERROR_MESSAGE);
        			System.out.println(ex);
        		}
        	}
        });
        
        addAppointmentButton = new JButton("Add appointment");
        addAppointmentButton.setEnabled(false);
        addAppointmentButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		new NewAppointment(selectedPatient);
        	}
        });
        
        findAppointment = new JButton("Find Appointments");
        findAppointment.setEnabled(false);
        findAppointment.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		new FindAppointment(selectedPatient);
        	}
        });

        // Generated code - do not modify
        GroupLayout changePlanLayout = new GroupLayout(changePlan.getContentPane());
        changePlan.getContentPane().setLayout(changePlanLayout);
        changePlanLayout.setHorizontalGroup(
            changePlanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(changePlanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(changePlanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(planComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(changePlanLayout.createSequentialGroup()
                        .addGap(0, 232, Short.MAX_VALUE)
                        .addComponent(updatePlan)))
                .addContainerGap())
        );
        changePlanLayout.setVerticalGroup(
            changePlanLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(changePlanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(planComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(changePlanLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(updatePlan))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(true);

        searchResults.setViewportView(searchResultsList);

        patientDetails.setBorder(BorderFactory.createEtchedBorder());

        addressField.setViewportView(addressArea);
        addressField.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addressField.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        addressField.setMinimumSize(new Dimension(12, 12));

        addressArea.setColumns(20);
        addressArea.setRows(5);

        healthcarePanel.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout gl_healthCarePanel = new GroupLayout(healthcarePanel);
        healthcarePanel.setLayout(gl_healthCarePanel);
        gl_healthCarePanel.setHorizontalGroup(
            gl_healthCarePanel.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gl_healthCarePanel.createSequentialGroup()
                .addContainerGap()
                .addGroup(gl_healthCarePanel.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(planNameArea, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, gl_healthCarePanel.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(subscribeButton)))
                .addContainerGap())
        );
        gl_healthCarePanel.setVerticalGroup(
            gl_healthCarePanel.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gl_healthCarePanel.createSequentialGroup()
                .addContainerGap()
                .addComponent(planNameArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(subscribeButton)
                .addContainerGap())
        );

        owedPanel.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout owedPanelLayout = new GroupLayout(owedPanel);
        owedPanelLayout.setHorizontalGroup(
        	owedPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(owedPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(owedPanelLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(owedField, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
        				.addComponent(receiptButton, Alignment.TRAILING))
        			.addContainerGap())
        );
        owedPanelLayout.setVerticalGroup(
        	owedPanelLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(owedPanelLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(owedField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(receiptButton)
        			.addContainerGap(63, Short.MAX_VALUE))
        );
        owedPanel.setLayout(owedPanelLayout);
        //basically .pack() but for JPanels
        owedPanel.setPreferredSize(owedPanel.getPreferredSize());
        owedPanel.validate();
        

        GroupLayout patientDetailsLayout = new GroupLayout(patientDetails);
        patientDetailsLayout.setHorizontalGroup(
        	patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(patientDetailsLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(patientDetailsLayout.createSequentialGroup()
        					.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(phoneLabel)
        						.addComponent(addressLabel)
        						.addComponent(nameLabel))
        					.addGap(68)
        					.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(addressField, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        						.addComponent(nameField, 301, 301, 301)
        						.addComponent(phoneField, 301, 301, 301)))
        				.addGroup(patientDetailsLayout.createSequentialGroup()
        					.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(healthcareLabel)
        						.addComponent(owedLabel))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(owedPanel, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
        						.addComponent(healthcarePanel, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)))
        				.addComponent(findAppointment, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        				.addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        				.addComponent(addAppointmentButton, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
        			.addContainerGap())
        );
        patientDetailsLayout.setVerticalGroup(
        	patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(patientDetailsLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(nameLabel))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(phoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(phoneLabel))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(addressLabel, Alignment.LEADING)
        				.addComponent(addressField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(7)
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(healthcareLabel)
        				.addComponent(healthcarePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(owedLabel)
        				.addComponent(owedPanel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
        			.addComponent(deleteButton)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(addAppointmentButton)
        			.addGap(5)
        			.addComponent(findAppointment)
        			.addContainerGap())
        );
        patientDetails.setLayout(patientDetailsLayout);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        					.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        					.addGap(18)
        					.addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(searchResults, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(patientDetails, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(searchButton))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(searchResults, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        				.addComponent(patientDetails, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        changePlan.pack();
        pack();
        setVisible(true);
    }

    // Instance methods
    
    // Refreshes frame with latest data
    private void refresh() {
    	getData("");
		updatePatientList();
		resetPatientDetails();
    }
    
    // Connects to database and gets relevant data
    private void getData(String searchedName) {
    	try(Connection connection = DBConnect.getConnection(false)){
    		patients = Patient.getPatients(connection, searchedName);
    		healthcarePlans = HealthcarePlan.getAll(connection);
    	}
    	catch(SQLException e){
    		DBConnect.printSQLError(e);
    	}
    }
    
    // Loads the list of patients
    private void updatePatientList() {
    	DefaultListModel<Patient> model = new DefaultListModel<>();
    	for(Patient patient: patients)
    		model.addElement(patient);
    	searchResultsList.setModel(model);
    }
    
    // Loads the list of HealthcarePlans
    private void updateHealthcarePlanList() {
    	DefaultComboBoxModel<HealthcarePlan> model = new DefaultComboBoxModel<>();
    	for(HealthcarePlan plan: healthcarePlans)
    		model.addElement(plan);
    	planComboBox.setModel(model);
    }
    
    // Clears/resets patient details panel
    private void resetPatientDetails() {
    	selectedPatient = null;
    	nameField.setText("");
    	addressArea.setText("");
    	phoneField.setText("");
    	planNameArea.setText("");
    	subscribeButton.setEnabled(false);
    	owedField.setText("");
    	receiptButton.setEnabled(false);
    	addAppointmentButton.setEnabled(false);
    	findAppointment.setEnabled(false);
    	deleteButton.setEnabled(false);
    }
    
    // Loads the details of the selected patient
    private void loadSelectedPatientDetails() {	
    	nameField.setText(selectedPatient.getForename() + " " + selectedPatient.getSurname());
    	addressArea.setEnabled(true);
    	addressArea.setText(selectedPatient.getAddress().getHouseNumber() + "\n" +
    						selectedPatient.getAddress().getStreet() + "\n" +
    						selectedPatient.getAddress().getCity() + "\n" +
    						selectedPatient.getAddress().getDistrict() + "\n" +
    						selectedPatient.getAddress().getPostCode());
    	phoneField.setText(selectedPatient.getPhoneNo());
    	planNameArea.setEnabled(true);
    	
    	// If patient has plan it fills in the plan details
    	if(selectedPatient.hasHealthCarePlan())
    		planNameArea.setText(selectedPatient.gethealthCarePlan().getPlan() + " - Started: " +  
    								selectedPatient.gethealthCarePlan().getStartDate() + ", Ends: " + 
    								selectedPatient.gethealthCarePlan().getEndDate());
    	else
    		planNameArea.setText("");
    	
		setSubscribeButtonText();
 
		DecimalFormat twoDecimals = new DecimalFormat("#0.00");
		owedField.setText(twoDecimals.format(selectedPatient.getBalance()));
		receiptButton.setEnabled(true);
    	
    	addAppointmentButton.setEnabled(true);
    	deleteButton.setEnabled(true);	
    	findAppointment.setEnabled(true);
    }
    
    // Activates the subscribe button depending on whether the patient has a plan or not
    private void setSubscribeButtonText() {
    	if(selectedPatient != null) {
    		if(selectedPatient.hasHealthCarePlan())
        		subscribeButton.setText("Unsubscribe");
    		else
    			subscribeButton.setText("Subscribe");
    	}
    	subscribeButton.setEnabled(true);
    }
    
    // Constants
    public static final int AMOUNT_TEXT_AREA_ROWS = 4;
    public static final int AMOUNT_TEXT_AREA_COLUMNS = 30;

    // System variables
    private ArrayList<Patient> patients;
    private ArrayList<HealthcarePlan> healthcarePlans;
    private Patient selectedPatient;
    
    // UI Variables
    private JButton searchButton;
    private JTextField searchField;
    private JScrollPane searchResults;
    private JList<Patient> searchResultsList;
    private JPanel patientDetails;
    private JTextArea addressArea;
    private JScrollPane addressField;
    private JLabel addressLabel;
    private JButton deleteButton;
    private JPanel healthcarePanel;
    private JLabel healthcareLabel;
    private JPanel owedPanel;
    private JLabel owedLabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JTextField phoneField;
    private JLabel phoneLabel;
    private JComboBox<HealthcarePlan> planComboBox;
    private JTextField planNameArea;
    private JButton subscribeButton;
    private JDialog changePlan;
    private JButton updatePlan;
    private JButton receiptButton;
    private JButton addAppointmentButton;
    private JButton findAppointment;
    private JTextField owedField;
}