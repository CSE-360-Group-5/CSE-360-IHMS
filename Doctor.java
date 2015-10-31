import javax.swing.*;
import java.awt.*;
import java.util.*;
public class Doctor {
	public static void main(String[] args){
		Jframe frame = new JFrame("Doctor");
		JPanel mainPanel = new JPanel;
		mainPanel.setLayout(BoxLayout.(mainPanel), BoxLayout.Y-AXIS);
		JButton labRecordsButton = new JButton("Lab Records");
		JButton hcButton = new JButton("View/Update Health Care Conditions");
		hcButton.addActionListener(hcButtonListener);
		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		frame.add(mainPanel);
		frame.setVisible(true);
		}
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
}
