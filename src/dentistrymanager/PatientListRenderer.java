package dentistrymanager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


@SuppressWarnings("serial")
public class PatientListRenderer extends JLabel implements ListCellRenderer<Patient> {
	
	public PatientListRenderer() {
		setOpaque(true);
	}
	
	public Component getListCellRendererComponent(JList<? extends Patient> list, Patient patient, int index, 
																			boolean isSelected, boolean cellHasFocus) {
		setText(patient.getForename() + " " + patient.getSurname());
        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

        // check if this cell is selected
        } else if (isSelected) {
            background = Color.RED;
            foreground = Color.WHITE;

        // unselected, and not the DnD drop location
        } else {
            background = Color.WHITE;
            foreground = Color.BLACK;
        };

        setBackground(background);
        setForeground(foreground);
		return this;
	}

}
