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

@SuppressWarnings("serial")
public class FindPatient extends JFrame {

    /**
     * Creates new form FindPatient
     */

    public FindPatient() {
		try(Connection connection = DBConnect.getConnection(false)){
			patients = Patient.getPatients(connection, "");
			healthcarePlans = HealthcarePlan.getAll(connection);
			selectedPatient = null;
		}
		catch(SQLException e){
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
        
        searchField = new JTextField();
        searchButton = new JButton();
        searchButton.setText("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enteredName = searchField.getText();
				try(Connection connection = DBConnect.getConnection(false)) {
					patients = Patient.getPatients(connection, enteredName);
					updatePatientList();
				} catch (SQLException ex) {
					DBConnect.printSQLError(ex);
				}
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
        			setSubscribeButtonText();
        		}
        	}
        });
        
        // Loads the list of patients when the menu is entered
    	updatePatientList();
        
        patientDetails = new JPanel();
        
        nameLabel = new JLabel();
        nameLabel.setText("Name:");
        nameField = new JTextField();
        nameField.setEditable(false);
        nameField.setEnabled(false);
        
        addressLabel = new JLabel();
        addressLabel.setText("Address:");
        addressField = new JScrollPane();
        addressArea = new JTextArea();
        addressArea.setEditable(false);
        addressArea.setEnabled(false);
        
        phoneLabel = new JLabel();
        phoneLabel.setText("Phone number:");
        phoneField = new JTextField();
        phoneField.setEditable(false);
        phoneField.setEnabled(false);
        
        healthcarePanel = new JPanel();
        healthcareLabel = new JLabel();
        healthcareLabel.setText("Healthcare plan:");
        planNameArea = new JTextField();
        planNameArea.setEditable(false);
        planNameArea.setEnabled(false);
        
        subscribeButton = new JButton();
        subscribeButton.setText("Subscribe");
        subscribeButton.setVisible(false);
        subscribeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!selectedPatient.hasHealthCarePlan())
					changePlan.setVisible(true);
				else
					try(Connection connection = DBConnect.getConnection(true)) {
						selectedPatient.unsubscribe(connection);
						loadSelectedPatientDetails();
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
        
        updatePlan = new JButton();
        updatePlan.setText("Update Plan");
        updatePlan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		HealthcarePlan selectedPlan = (HealthcarePlan)planComboBox.getSelectedItem();
        		try(Connection connection = DBConnect.getConnection(true)) {
        			selectedPatient.subscribe(connection, selectedPlan);
        			loadSelectedPatientDetails();
        		} catch(SQLException ex) {
        			DBConnect.printSQLError(ex);
        		} catch(DuplicateKeyException ex) {
        			System.out.println(ex.getTable());
        		}
        	}
        });
        
        owedPanel = new JPanel();
        
        owedLabel = new JLabel();
        owedLabel.setText("Owed:");
        amountOwedList = new JTextArea(AMOUNT_TEXT_AREA_ROWS, AMOUNT_TEXT_AREA_COLUMNS);
        amountOwedList.setEnabled(false);
        
        receiptButton = new JButton(); 
        receiptButton.setText("Receipt");
        receiptButton.setEnabled(false);
        receiptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	// #TODO
            }
        });
        
        printReceipt = new JDialog();
        printReceipt.setAlwaysOnTop(true);
        printRecieptPayButton = new JButton();
        printRecieptPayButton.setText("Pay All");
        printRecieptPayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	 // #TODO button action
            }
        });
        printRecieptCloseButton = new JButton();
        printRecieptCloseButton.setText("Close");
        
        deleteButton = new JButton();
        deleteButton.setText("Delete Patient");
        deleteButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		try(Connection connection = DBConnect.getConnection(true)) {
        			selectedPatient.delete(connection);
        			selectedPatient = null;
        		} catch (SQLException ex) {
        			DBConnect.printSQLError(ex);
        		} catch (DeleteForeignKeyException ex) {
        			System.out.println(ex);
        		}	
        	}
        });
        
        addAppointmentButton = new JButton();
        addAppointmentButton.setText("Add appointment");
        addAppointmentButton.setEnabled(false);
        addAppointmentButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		new NewAppointment(selectedPatient);
        	}
        });

        jScrollPane1 = new JScrollPane();

        // Generated code - do not modify
        GroupLayout printReceiptLayout = new GroupLayout(printReceipt.getContentPane());
        printReceipt.getContentPane().setLayout(printReceiptLayout);
        printReceiptLayout.setHorizontalGroup(
            printReceiptLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(printReceiptLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(printReceiptLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(printReceiptLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(printRecieptPayButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(printRecieptCloseButton)))
                .addContainerGap())
        );
        printReceiptLayout.setVerticalGroup(
            printReceiptLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(printReceiptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(printReceiptLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(printRecieptPayButton)
                    .addComponent(printRecieptCloseButton))
                .addContainerGap(13, Short.MAX_VALUE))
        );

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
        setMinimumSize(new Dimension(700, 500));
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
        owedPanel.setLayout(owedPanelLayout);
        owedPanelLayout.setHorizontalGroup(
            owedPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(owedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(owedPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(amountOwedList, GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.TRAILING, owedPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(receiptButton)))
                .addContainerGap())
        );
        owedPanelLayout.setVerticalGroup(
            owedPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(owedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(amountOwedList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(receiptButton)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        GroupLayout patientDetailsLayout = new GroupLayout(patientDetails);
        patientDetailsLayout.setHorizontalGroup(
        	patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(patientDetailsLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(deleteButton, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        				.addComponent(addAppointmentButton, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
        				.addGroup(patientDetailsLayout.createSequentialGroup()
        					.addGroup(patientDetailsLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(patientDetailsLayout.createSequentialGroup()
        							.addComponent(phoneLabel)
        							.addGap(18))
        						.addComponent(addressLabel)
        						.addComponent(nameLabel))
        					.addGap(50)
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
        						.addComponent(healthcarePanel, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))))
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
        				.addComponent(owedPanel, GroupLayout.PREFERRED_SIZE, 102, Short.MAX_VALUE))
        			.addGap(18)
        			.addComponent(deleteButton)
        			.addComponent(addAppointmentButton)
        			.addContainerGap())
        );
        patientDetails.setLayout(patientDetailsLayout);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
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
        				.addComponent(searchResults, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(patientDetails, GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
        setVisible(true);
    }

    // Instance methods
    
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
    
    // Loads the details of the selected patient
    private void loadSelectedPatientDetails() {
    	
    	nameField.setEnabled(true);
    	nameField.setText(selectedPatient.getForename() + " " + selectedPatient.getSurname());
    	addressArea.setEnabled(true);
    	addressArea.setText(selectedPatient.getAddress().getHouseNumber() + "\n" +
    						selectedPatient.getAddress().getStreet() + "\n" +
    						selectedPatient.getAddress().getCity() + "\n" +
    						selectedPatient.getAddress().getDistrict() + "\n" +
    						selectedPatient.getAddress().getPostCode());
    	phoneField.setEnabled(true);
    	phoneField.setText(selectedPatient.getPhoneNo());
    	planNameArea.setEnabled(true);
    	
    	// If patient has plan it fills in the plan details
    	if(selectedPatient.hasHealthCarePlan())
    		planNameArea.setText(selectedPatient.gethealthCarePlan().getPlan() + " start date: " +  
    								selectedPatient.gethealthCarePlan().getStartDate() + " ends: " + 
    								selectedPatient.gethealthCarePlan().getEndDate());
    	
    	// Fills in the amount owed details
    	ArrayList<String> amountOwedDetails = new ArrayList<>();
    	try(Connection connection = DBConnect.getConnection(false)) {
    		amountOwedDetails = selectedPatient.getAmountOwed(connection);
    	} catch (SQLException e) {
    		DBConnect.printSQLError(e);
    	}
    	
    	for(String amountOwed: amountOwedDetails)
    		amountOwedList.append(amountOwed);	
    	
    	// Add appointment button enabled
    	addAppointmentButton.setEnabled(true);
    	
    }
    
    // Activates the subscribe button depending on whether the patient has a plan or not
    private void setSubscribeButtonText() {
    	if(selectedPatient != null) {
    		subscribeButton.setVisible(true);
    		if(selectedPatient.hasHealthCarePlan())
        		subscribeButton.setText("Unsubscribe");
    	}
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
    private JButton planClosebutton;
    private JComboBox<HealthcarePlan> planComboBox;
    private JTextField planNameArea;
    private JButton subscribeButton;
    private JDialog changePlan;
    private JButton updatePlan;
    private JDialog printReceipt;
    private JButton printRecieptCloseButton;
    private JButton printRecieptPayButton;
    private JButton receiptButton;
    private JButton addAppointmentButton;
    private JScrollPane jScrollPane1;
    private JTextArea amountOwedList;
}