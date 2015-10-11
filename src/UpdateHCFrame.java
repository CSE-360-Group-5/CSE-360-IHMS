import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class UpdateHCFrame extends JPanel{
	public UpdateHCFrame () throws FileNotFoundException{
		super();
		JTextField conditions = new JTextField();
		JButton edit = new JButton("Edit");
		JButton save = new JButton("Save");
		this.add(conditions);
		this.add(edit);
		FileReader hc = new FileReader(getPatientConditions());
	}
	
	public class ButtonListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e){
			
		}
	}
	
	public class SaveButtonListern implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
		}
	}

	
	public class HCListener implements DocumentListener{
		public void changedUpdate(DocumentEvent e){
			
		}
		
		public void insertUpdate(DocumentEvent e){
			
		}
		
		public void removeUpdate(DocumentEvent e){
			
		}
	}
	
	public File getPatientConditions(){
		return new File("/Users/timothymillea/Documents/CSE360/TestPatient");
		
	}
}
