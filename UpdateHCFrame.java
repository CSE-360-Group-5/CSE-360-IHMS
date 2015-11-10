import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateHCFrame extends JPanel{
	private JTextField patientID;
	private JTextField hccID;
	private JTextArea conditions;
	private JTextField date;
	JButton save;
	BufferedWriter writer;
	BufferedReader reader;
	JScrollPane scrollConditions;
	JScrollPane scrollList;
	JTextField alert;
	private Vector<HCC> hccVector;
	private JList<HCC> hccList;
	
	//Database
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	//Constructor
	
	public UpdateHCFrame () throws IOException{
		super();
		try
		{
			Class.forName("java.sql.Driver");
			
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url,"root","admin");
		}
		
		catch(Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
		patientID = new JTextField(10);
		hccID = new JTextField(10);
		conditions = new JTextArea(20, 50);
		scrollConditions = new JScrollPane(conditions);
		save = new JButton("Save");
		
		this.add(patientID);
		this.add(hccID);
		this.add(conditions);
		this.add(save);
		SaveButtonListener saver = new SaveButtonListener();
		save.addActionListener(saver);
		
		hccVector = new Vector<HCC>();
		
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM hcc");
			
			while(rs.next())
			{
				HCC obj = new HCC();
				obj.setPatientID(rs.getString(1));
				obj.sethccID(rs.getString(2));
				obj.setHCC(rs.getString(3));
				obj.setDate(rs.getString(4));
				
				hccVector.add(obj);
			}
		}
		
		catch (Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
		hccList = new JList<HCC>();
		scrollList = new JScrollPane(hccList);
		hccList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hccList.addListSelectionListener(new ListListener());
		
		
		alert = new JTextField();
		this.add(alert);
		alert.setVisible(false);
		while(reader.ready()){
			conditions.append(reader.readLine()); //Reads file to text area
			conditions.append("\n");
		}
		//FileReader hc = new FileReader(getPatientConditions());
	}
	
	//public class ButtonListener implements ActionListener{ 
	//	public void actionPerformed(ActionEvent e){
			
	//	}
	//}
	
	private class ListListener implements ListSelectionListener
	{
		HCC hcc1 = new HCC();
		public void valueChanged(ListSelectionEvent e)
		{
			hcc1 = hccList.getSelectedValue();
			
			patientID.setText(hcc1.getPatientID());
			hccID.setText(hcc1.gethccID());
			conditions.setText(hcc1.getHCC());
			date.setText(hcc1.getDate());
			
		}
	}
	
	public class SaveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			HCC hcc1 = new HCC();
			
			String _patientID = patientID.getText();
			String _hccID = hccID.getText();
			String _conditions = conditions.getText();
			String _date = date.getText();
			
			hcc1.setPatientID(_patientID);
			hcc1.sethccID(_hccID);
			hcc1.setHCC(_conditions);
			hcc1.setDate(_date);
			
			if(hccList.getSelectedIndex() > 0)
			{
				int selectedIndex = hccList.getSelectedIndex();
				hccVector.remove(selectedIndex);
				hccVector.add(selectedIndex, hcc1);
				
				try
				{
					statement = conn.createStatement();
					statement.executeUpdate("UPDATE hcc SET " + "`patientID`='" + _patientID + "', `HCC`='" + _date + "' WHERE `hccID`='" + (selectedIndex) + "';");
				}
				
				catch (Exception e1)
				{
					System.err.println("Got an exception!");
					System.err.println(e1.getMessage());
				}
				
			}
			
			else
			{
				hccVector.add(hcc1);
				
				try
				{
					statement = conn.createStatement();
					statement.executeUpdate("INSERT INTO hcc ((patientID, HCC, date)" + "VALUES ('" + _patientID + "', '" + _conditions + "', '" + _date + "')");
				}
				
				catch (Exception e2)
				{
					System.err.println("Got an exception!");
					System.err.println(e2.getMessage());
				}
				
				_patientID = "";
				_hccID = "";
				_conditions = "";
				_date = "";
			}
			hccList.updateUI();
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
