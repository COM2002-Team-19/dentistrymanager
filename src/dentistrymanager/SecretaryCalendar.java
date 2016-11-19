package dentistrymanager;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecretaryCalendar extends JFrame {

    /**
     * Creates new form SecretaryCalendar
     */
    public SecretaryCalendar() {
    	try(Connection connection = DBConnect.getConnection(false)){
    		this.partners = Partner.getAll(connection);
    		this.dentist = partners.get(0);
    		this.hygienist = partners.get(1);
    		this.dentistAppointments = dentist.getWeekAppointments(connection, 1);
    		this.hygienistAppointment = hygienist.getWeekAppointments(connection, 1);
    		
    	}
    	catch(SQLException e){
    		DBConnect.printSQLError(e);
    	}
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        dentistCalendarList.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        dentistCalendarListPane.setViewportView(dentistCalendarList);

        // Buttons
        dentistAddAppButton.setText("Add appointment");
        dentistDeleteAppButton.setText("Delete appointment");
        
        hygienistAddAppButton.setText("Add appointment");
        hygienistDeleteAppButton.setText("Delete appointment");

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

        hygienistCalendarList.setModel(new AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
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
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SecretaryCalendar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SecretaryCalendar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SecretaryCalendar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(SecretaryCalendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SecretaryCalendar().setVisible(true);
            }
        });
    }
    
    // System variables
    private ArrayList<Partner> partners;
    private Partner dentist;
    private Partner hygienist;
    private ArrayList<Appointment> dentistAppointments;
    private ArrayList<Appointment> hygienistAppointment;

    // Variables declaration - do not modify
    private JButton dentistAddAppButton;
    private JList<String> dentistCalendarList;
    private JScrollPane dentistCalendarListPane;
    private JButton dentistDeleteAppButton;
    private JPanel dentistTabPanel;
    private JButton hygienistAddAppButton;
    private JList<String> hygienistCalendarList;
    private JScrollPane hygienistCalendarListPane;
    private JButton hygienistDeleteAppButton;
    private JPanel hygienistTabPanel;
    private JTabbedPane secretaryCalendarTabPane;
    // End of variables declaration
}
