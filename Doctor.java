import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DoctorFrame extends JPanel{
	JFrame frame;
	JPanel mainPanel;
	JButton labRecordsButton;
	JButton hcButton;
	JButton ePrescribeButton;
	JButton cancel;
	JButton save;
	JTextField nameField;
	JTextField DOBField;
	JTextField prescriptionField;
	String name;
	String DOB;
	String prescription;
	String filename = "test.txt";
	Writer writer;
	
	
	public DoctorFrame() throws IOException{
		super();
		frame = new JFrame("Doctor");
		mainPanel = new JPanel;
		mainPanel.setLayout(BoxLayout.(mainPanel), BoxLayout.Y-AXIS);
		
		labRecordsButton = new JButton("Lab Records");
		hcButton = new JButton("View/Update Health Care Conditions");
		ePrescribeButton = new JButton("e-Prescribe");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		
		nameField = new JTextField(name, 20);
		DOBField = new JTextField(DOB,20);
		prescriptionField = new JTextField(prescription, 50);
		
		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		mainPanel.add(ePrescribeButton);
		
		hcButtonListener hcListener = new hcButtonListener();
		labButtonListener labListener = new labButtonListener();
		ePrescribeButtonListener ePrescribeListener = new ePrescribeButtonListener(); 
		SaveEPButtonListener saveEP = new SaveEPButtonListener();
		
		hcButton.addActionListener(hcListener);
		labRecordsButton.addActionListener(labListener);
		ePrescribeButton.addActionListener(ePrescribeListener);
		
		frame.add(mainPanel);
		frame.setVisible(true);
	}
	
	public class SaveEPButtonListener implements ActionListener{
		void actionPerformed(ActionEvent e){
			try{
				writer = new BufferedWriter(new FileWriter(filename));
				name = nameField.getText();
				DOB = DOBField.getText();
				prescription = prescriptionField.getText();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try{
				writer.write(name+"\r\n");
				writer.write(DOB+"\r\n");
				writer.write(prescription);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
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
	public class ePrescribeButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			try{
				this.remove(labRecordsButton);
				this.remove(hcButton);
				this.remove(ePrescribeButton);
				
				this.add(nameField);
				this.add(DOBField);
				this.add(prescriptionField);
				this.add(save);
				this.add(cancel);
				
				save.addActionListener(saveEP);
			}
		}
	}
	
}
