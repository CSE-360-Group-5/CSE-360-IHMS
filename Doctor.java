import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DoctorFrame extends JPanel{
	JFrame frame;
	JPanel mainPanel;
	JButton labRecordsButton;
	JButton hcButton;
	JButton ePrescribeButton;
	
	public DoctorFrame() throws IOException{
		super();
		frame = new JFrame("Doctor");
		mainPanel = new JPanel;
		mainPanel.setLayout(BoxLayout.(mainPanel), BoxLayout.Y-AXIS);
		labRecordsButton = new JButton("Lab Records");
		hcButton = new JButton("View/Update Health Care Conditions");
		ePrescribeButton = new JButton("e-Prescribe");
		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		mainPanel.add(ePrescribeButton);
		frame.add(mainPanel);
		frame.setVisible(true);
	}
}

/*
public class Doctor {
	public static void main(String[] args){
		JFrame frame = new JFrame("Doctor");
		JPanel mainPanel = new JPanel;
		mainPanel.setLayout(BoxLayout.(mainPanel), BoxLayout.Y-AXIS);
		JButton labRecordsButton = new JButton("Lab Records");
		JButton hcButton = new JButton("View/Update Health Care Conditions");
		JButton ePrescribeButton = new JButton("e-Prescribe");
		hcButton.addActionListener(hcButtonListener);
		labRecordsButton.addActionListener(labButtonListener);
		ePrescribeButton.addActionListener(ePrescribeButtonListener);
		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		mainPanel.add(ePrescribeButton);
		frame.add(mainPanel);
		frame.setVisible(true);
		}*/
	public class hcButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			Jframe hcFrame = new JFrame("Health Care Conditions");
			UpdateHCFrame hcPanel = new UpdateHCFrame();
		}
	}
	public class labButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			LabRecords.main(args);
		}
	}
	public class ePrescribeButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			
		}
	}
}
