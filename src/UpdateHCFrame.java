import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.sql.*;
public class UpdateHCFrame extends JPanel{
	JTextArea conditions;
	JButton edit;
	JButton save;
	String filename;
	BufferedWriter writer;
	BufferedReader reader;
	JTextField alert;
	Connection conn = null;
	ResultSet rs = null;
	Statement stmt = null;
	public UpdateHCFrame () throws IOException, SQLException{
		super();
		conditions = new JTextArea(20, 20);
		//JButton edit = new JButton("Edit");
		JButton save = new JButton("Save");
		this.add(conditions);
		//this.add(edit);
		this.add(save);
		SaveButtonListener saver = new SaveButtonListener();
		save.addActionListener(saver);
		filename = "test.txt";
		reader = new BufferedReader(new FileReader(filename));
		alert = new JTextField();
		this.add(alert);
		alert.setVisible(false);
		
		try{
			Class.forName("java.sql.Driver");
			String url = "jdbc:mysql://localhost:3306/project";
			conn = DriverManager.getConnection(url, "root", "D3ruut68%%");
			System.out.println("connected");
			stmt = conn.createStatement();
			stmt.execute("INSERT INTO hpc (hpcRecords) VALUES (0)");
			//rs = stmt.executeQuery("SELECT * FROM hpc");
		}
		catch(Exception e){
			System.out.println("Could not connect to database");
			System.out.println(e.getMessage());
		}
		if(rs != null){
			conditions.append((String) rs.getObject(ALLBITS));
		}
		//FileReader hc = new FileReader(getPatientConditions());
	}
	
	//public class ButtonListener implements ActionListener{ 
	//	public void actionPerformed(ActionEvent e){
			
	//	}
	//}
	
	public class SaveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				writer = new BufferedWriter(new FileWriter(filename)); //Saves the edits
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				if(rs == null){
					String cond = conditions.getText();
					stmt.execute("INSERT INTO hpc" + "VALUES ("+ cond +")");
				}
				else{
					String cond = conditions.getText();
					rs = stmt.executeQuery("SELECT hpcRecords from hpc");
					stmt.execute("UPDATE hpc SET hpcRecords = ("+ cond +")");
				}
				JOptionPane.showMessageDialog(alert,"Patient conditions updated.");
				String[] emergency = {"Emergency", "emergency", "Severe", "severe", "Urgent", "urgent", "Critical", "critical", "Excessive", "excessive", "Broken", "broken", "Prolonged", "prolonged", "Seizure", "seizure"};
				for(int i = 0; i < emergency.length; i++){
					if(conditions.getText().contains(emergency[i])){
						JFrame newFrame = null;
						JOptionPane.showMessageDialog(newFrame, "Emergency: Notify EMS");
						break;
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{
			try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
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
