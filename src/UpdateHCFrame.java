import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class UpdateHCFrame extends JPanel{
	public UpdateHCFrame (){
		super();
		JTextField conditions = new JTextField();
		JButton edit = new JButton("Edit");
		this.add(conditions);
		this.add(edit);
	}
	
	public class ButtonListener implements ActionListener{ 
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
}
