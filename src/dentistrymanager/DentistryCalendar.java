package dentistrymanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class DentistryCalendar extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		new DentistryCalendar();
	}

	public DentistryCalendar() {Error: Could not find or load main class dentistrymanager.gui.DentistryCalendar

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(0, 2));
		setContentPane(contentPane);
		
		JPanel currentAppointment = new JPanel();
		currentAppointment.setLayout(new GridLayout(0,1));
		
		JPanel nextAppointment = new JPanel();
		nextAppointment.setLayout(new GridLayout(0,1));
		JLabel nextAppTitle = new JLabel("Next Appointment:");
		JTextArea nextAppDisplay = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(nextAppDisplay); 
		nextAppDisplay.setEditable(false);
		setVisible(true);
	}
}
