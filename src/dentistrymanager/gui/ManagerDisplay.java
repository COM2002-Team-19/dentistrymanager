package dentistrymanager.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

public class ManagerDisplay extends JFrame {

	private Container contentPane;
	private int width;
	private int height;
	
	public ManagerDisplay() {
		//Set size, position and location
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int scrWidth = (int) screenSize.getWidth();
		int scrHeight = (int) screenSize.getHeight();
		width = 3*scrWidth/4;
		height = 3*scrHeight/4;
		
		this.setBounds(scrWidth/8,scrHeight/8,width,height); //Default frame takes 75% of screen
		this.setTitle("Dentistry Manager"); //Default frame title
		contentPane = this.getContentPane();
	}
	
	public void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SplashScreen sp = new SplashScreen();
		contentPane.add(sp);
		setVisible(true);
	}
	
	public static void main(String[] args) throws IOException {
		//Set up
		ManagerDisplay display = new ManagerDisplay();
		display.init();
	}
}