///////////////////////////////////////////////////////////////////////////////////////
// Name: MakeAppointments.java
// Authors: Gareth Whitehead, Brandon Namie, Joshua Lidell
// Description: Create content of GUI to make and view appointments
// Date: 10/20/2015
///////////////////////////////////////////////////////////////////////////////////////

//Import packages
import javax.swing.*; 
import javax.swing.table.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MakeAppointments extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	static int del;
	static int ryear, rmonth, rday, currentYear, currentMonth, currentRow, currentCol;
	static JLabel lmonth, lyear;
	static JButton prev, next, canc;
	static JScrollPane scal; //The scrollpane
	static JPanel pcal;
	static JTable Cal;
	static Container pane;
	static DefaultTableModel mcal; //Table model
	static JComboBox cyear, docName, appTime, docType;
	static Vector person = new Vector();
	static Connection conn;
	static Statement statement;
	static ResultSet rs;
	static int id;
	static int patient_id;
	
	public MakeAppointments(int patientID)
	{
		try
	    {
	      // Step 1: "Load" the JDBC driver
	      Class.forName("java.sql.Driver"); 

	      // Step 2: Establish the connection to the database 
	      String url = "jdbc:mysql://localhost:3306/cse"; 
	      conn = DriverManager.getConnection(url,"root","admin"); 
	    }

	    catch (Exception e)
	    {
	      System.err.println("D'oh! Got an exception!"); 
	      System.err.println(e.getMessage()); 
	    } 
		
		try{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM appointments WHERE `patientID` = " + patientID);
			while(rs.next()){
				Appointment a1 = new Appointment(rs.getString("docName"),rs.getString("month"),rs.getString("year"),rs.getString("day"),rs.getString("time"),rs.getString("docType"),rs.getInt("row"),rs.getInt("col"),rs.getInt("patientID"),rs.getInt("appointmentsID"));
				person.add(a1);
			}
		}
		catch (SQLException e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		//style that is necessary
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		
		patient_id = patientID;

		//controls and portions of Calendar
		lmonth = new JLabel ("January");
		lyear = new JLabel ("Change year:");
		cyear = new JComboBox();
		prev = new JButton ("<<");
		next = new JButton (">>");
		canc= new JButton("Cancel");
		mcal = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex){
				return false;
			}
		};
		Cal = new JTable(mcal);
		scal = new JScrollPane(Cal);
		pcal = new JPanel(null);
 
		canc.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent event) {
	               System.exit(0);
	          }
	       });
		
		//action listeners for buttons and the like
		prev.addActionListener(new btnPrev_Action());
		next.addActionListener(new btnNext_Action());
		cyear.addActionListener(new cmbYear_Action());
		Cal.addMouseListener(new mouseCont());
		
		//Adding the elements to the pane
		pcal.add(lmonth);
		pcal.add(cyear);
		pcal.add(prev);
		pcal.add(next);
		pcal.add(canc);
		pcal.add(scal);
		add(pcal);
		
		//Setting where the elements are on the pane
		pcal.setBounds(0, 0, 320, 335);
		lmonth.setBounds(160-lmonth.getPreferredSize().width/2, 25, 100, 25);
		canc.setBounds(10, 305, 80, 20);
		cyear.setBounds(215, 305, 100, 20);
		prev.setBounds(10, 25, 50, 25);
		next.setBounds(260, 25, 50, 25);
		scal.setBounds(10, 50, 300, 250);
		
		//Make frame unresizable
		setResizable(false);
		
		//Inner workings for the day mechanism
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		rday = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		rmonth = cal.get(GregorianCalendar.MONTH); //Get month
		ryear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = rmonth; //Match month and year
		currentYear = ryear;
				
		//Add days
		String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All of the days
		for (int i=0; i<7; i++){
			mcal.addColumn(days[i]);
		}
		
		Cal.getParent().setBackground(Cal.getBackground()); //Set background

		//No resize/reorder
		Cal.getTableHeader().setResizingAllowed(false);
		Cal.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		Cal.setColumnSelectionAllowed(true);
		Cal.setRowSelectionAllowed(true);
		Cal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		Cal.setRowHeight(38);
		mcal.setColumnCount(7);
		mcal.setRowCount(6);
		
		//Placing the dates in the cells
		for (int i=ryear-100; i<=ryear+100; i++){
			cyear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (rmonth, ryear); //Refresh calendar
	
		//Standard preparation for a frame
		setTitle("Schedule Appointments"); //Create and name frame
		setSize(330, 375); //Set size to 400x400 pixels
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
	}

	public static void refreshCalendar(int month, int year){
		//instantiation
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int numoday, startom; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		prev.setEnabled(true);
		next.setEnabled(true);
		if (month == 0 && year <= ryear){prev.setEnabled(false);} //Cannot set an appointment back in time
		if (month == 11 && year >= ryear+50){next.setEnabled(false);} //Too early to set an appointment
		lmonth.setText(months[month]); //Refresh the month label (at the top)
		lmonth.setBounds(160-lmonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		cyear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//deletes current table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mcal.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numoday = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startom = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Create calendar
		for (int i=1; i<=numoday; i++){
			int row = (i+startom-2)/7;
			int column  =  (i+startom-2)%7;
			mcal.setValueAt(String.valueOf(i) , row, column);//(i, row, column);
		}

		//Apply renderers
		Cal.setDefaultRenderer(Cal.getColumnClass(0), new tblCalendarRenderer());
	}
	//adds a bit of color style to calendar
	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
								
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == rday && currentMonth == rmonth && currentYear == ryear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			
			int i=0;
			while(i < person.size())
			{
				Appointment a1 = (Appointment) person.get(i);
				//add code here cycling through the array list and testing each possibility of columns and rows stored in the arraylist of appointments
				if(a1.getRow() == row && a1.getColumn() == column){
					setBackground(new Color(255, 255, 51));
				}
				i++;
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static int existsA(String month, int year, String day){
		int i = 0;
		while(i<person.size())
		{
			Appointment a1 = (Appointment) person.get(i);
			if(a1.getYear().equals(Integer.toString(year)))
			{
				if(a1.getMonth().equals(month))
				{
					if(a1.getDate().equals(day))
					{
						return i;
					}
				}
			}
			i++;
		}
		
		return -1;
	}

	static class mouseCont extends MouseAdapter{//use this to access get another frame pulled up to note down appointment time and what not
		public void mouseClicked(MouseEvent e){
			int row=Cal.getSelectedRow();//this will give you the row number
			int col=Cal.getSelectedColumn();//this will give you the column number
			int mon=currentMonth+1;
			String day=Cal.getValueAt(row, col).toString();
			int d= Integer.parseInt(day);
			System.out.println(d);
			if(row!=-1 && col!=-1 && Cal.getValueAt(row, col)!=null && existsA(Integer.toString(mon),currentYear,Cal.getValueAt(row, col).toString())==-1){//this now will only activate when there is an actual date associated with that day
				input(row,col,currentYear,d);
			}
			else if(row!=-1 && col!=-1 && Cal.getValueAt(row, col)!= null && existsA(Integer.toString(mon),currentYear,Cal.getValueAt(row, col).toString())!=-1){
				del = existsA(Integer.toString(mon),currentYear,Cal.getValueAt(row, col).toString());
				Appointment a2 = (Appointment) person.get(del);
				inputEdit(a2.getDoctor(),a2.getTime(), a2.getMonth()+"/"+a2.getDate()+"/"+a2.getYear());
			}
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cyear.getSelectedItem() != null){
				String b = cyear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}

	static String[] input(final int d, final int m, int y, int d1){//input frame for schedule
		String[] out=new String[4];//appointment info storage
		Vector doctors = new Vector();
		try{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM staff WHERE `staffType` = 'Doctor';");
			String fullName="";
			while(rs.next()){
				fullName = rs.getString("fname") + " " + rs.getString("lname");
				doctors.add(fullName);
			}
		}
		catch (SQLException e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		final String[] doctorType = {"Pediatric", "Vascular", "Neurologic", "Gynocology", "Orthopedic", "Other"};
		
		currentRow = d;
	    currentCol = m;
	    
		final JFrame input=new JFrame("Make Appointment");
		Container pane1;
		JPanel pan=new JPanel(null);
		JLabel doctor=new JLabel("Choose your Doctor:");
		JLabel doctorT = new JLabel("Doctor Type");
		JLabel da=new JLabel("Enter Date:");
		JLabel time=new JLabel("Appointment Time:");
		JButton submit=new JButton("Submit");
		docName = new JComboBox(doctors);
		docType = new JComboBox(doctorType);
	
		final JFormattedTextField dateField = new JFormattedTextField(new SimpleDateFormat("MM/dd/yyyy"));
		java.util.Date date = new java.util.Date();
		date.setDate(d1);
		dateField.setValue(date); // today
		
		final JFormattedTextField timeField = new JFormattedTextField(new SimpleDateFormat("hh:mm a"));
		timeField.setValue(new java.util.Date()); // today
		
		input.setSize(330, 375); //Set size to 400x400 pixels
		pane1 = input.getContentPane();
		pane1.setLayout(null); //Apply null layout
		pan.setLayout(new GridLayout(5,2,20,50));
		pane1.setSize((int)(input.getHeight()/2),(int)(input.getWidth()/2));
		pane1.setLocation(input.getHeight()-((int)(input.getHeight()/2)),input.getWidth()-((int)(input.getWidth()/2)));//trying to center failed
		input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
		input.setAlwaysOnTop(true);
		
		pan.add(da);
		pan.add(dateField);
		pan.add(time);
		pan.add(timeField);
		pan.add(doctorT);
		pan.add(docType);
		pan.add(doctor);
		pan.add(docName);
		pan.add(submit);
		pane1.add(pan);
		
		 submit.addActionListener(new ActionListener()
		 {
	           public void actionPerformed(ActionEvent event) 
	           {//need to find a way to get user input from the jframe and create a new appointment and a entry into the array list
	        	   	        	   
	        	   String name = docName.getSelectedItem().toString();
	        	   String date = dateField.getText().toString();
	        	   String time = timeField.getText().toString(); 
	        	   String docT = docType.getSelectedItem().toString();
	        	   String[] d = date.split("/");
	        	   
	        	   Statement stat;
	        	   
				try 
				{
					statement = conn.createStatement();
					rs = statement.executeQuery("SELECT * FROM appointments");
					while(rs.next()){
						id = rs.getInt(1);
					}
					id++;
					
					stat = conn.createStatement();
		        	stat.executeUpdate("INSERT INTO appointments (appointmentsID, patientID, docType, docName, day, month, year, time, row, col, finalized)" + " VALUES ( " + id + ", " + patient_id + ", '" + docT + "', '" + name + "', " + d[1] + ", " + d[0] +", " + d[2] + ", '" + time + "', " + currentRow + ", " + currentCol + ", 'false')");
		        												 
		        	Appointment list = new Appointment(name, d[0], d[2], d[1], time, docT, currentRow, currentCol, patient_id,id);
		        	
					person.add(list);
					for(int i =0; i< person.size(); i++)
					{
						Appointment a = (Appointment) person.get(i);
						System.out.println("col: " + a.getColumn() + "; Row: " + a.getRow());
						System.out.println();
					}
				}
				catch (SQLException e)
				{
					System.err.println("Got an exception!");
					System.err.println(e.getMessage());
				}	        	   
	        	        	   
	        	   for(int i = 0; i < person.size(); i++) {
	        		   Appointment a2 = (Appointment) person.get(i);
	        		    System.out.println(a2.getMonth());  
	        		} 
	        	   
	        	   input.setVisible(false); 
	        	}
	           
	       });
		
		pan.setBounds(0, 0, 320, 335);
		input.setResizable(false);
		input.setVisible(true);
		input.setAlwaysOnTop(true);
		return out;
	}
	static void inputEdit(String doc, String timeApp, String date)
	{
		final JFrame inputEdit = new JFrame("View/Manage Appointment");
		inputEdit.setAlwaysOnTop(true);
		Container pane2;
		JPanel panEdit = new JPanel(null);
		JLabel doctorEdit = new JLabel("Doctor:");
		JLabel doctorName = new JLabel(doc);//change back
		JLabel dateEdit = new JLabel("Date:");
		JLabel dateApp = new JLabel(date); //change back
		JLabel timeEdit = new JLabel("Appointment Time:");
		JLabel timeAppLab = new JLabel(timeApp); //change back
		JButton cancel = new JButton("Cancel Appointment");
		JButton ok = new JButton("Ok");
		inputEdit.setSize(330, 375); //Set size to 400x400 pixels
		pane2 = inputEdit.getContentPane();
		pane2.setLayout(null); //Apply null layout
		panEdit.setLayout(new GridLayout(4,2,20,50));
		pane2.setSize((int)(inputEdit.getHeight()/2),(int)(inputEdit.getWidth()/2));
		pane2.setLocation(inputEdit.getHeight()-((int)(inputEdit.getHeight()/2)),inputEdit.getWidth()-((int)(inputEdit.getWidth()/2)));//trying to center failed
		inputEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
		pane2.add(panEdit);
		panEdit.add(dateEdit);
		panEdit.add(dateApp);
		panEdit.add(timeEdit);
		panEdit.add(timeAppLab);
		panEdit.add(doctorEdit);
		panEdit.add(doctorName);
		panEdit.add(ok);
		panEdit.add(cancel);
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm cancellation", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	int row=Cal.getSelectedRow();//this will give you the row number
					int col=Cal.getSelectedColumn();//this will give you the column number
					int mon=currentMonth+1;
					int dellID=0;
					
		        	for(int i = 0; i<person.size(); i++)
		        	{
		        		Appointment delApp = (Appointment) person.get(i);
		        		if(delApp.getColumn() == col && delApp.getRow() == row && delApp.getMonth().equals(Integer.toString(mon)) && delApp.getYear().equals(Integer.toString(currentYear)))
		        		{
		        			dellID = delApp.getId();
		        			person.removeElement(delApp);
		        		}
		        	}
		        	
		        	try 
					{
						//statement = conn.createStatement();
						if(dellID != 0){
							PreparedStatement st = conn.prepareStatement("DELETE FROM `cse`.`appointments` WHERE `appointmentsID`='" + dellID + "';");
							st.executeUpdate(); 
						}
					}
					catch (SQLException e)
					{
						System.err.println("Got an exception!");
						System.err.println(e.getMessage());
					}
		        	
		        	//DELETE FROM `cse`.`appointments` WHERE `appointmentsID`='6';
					inputEdit.setVisible(false);
		        }
		        else {}				
				//refresh calendar
			}
		});
		
	ok.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event)
		{
			inputEdit.setVisible(false);
		}
	});
	
	panEdit.setBounds(0, 0, 320, 335);
	inputEdit.setResizable(false);
	inputEdit.setVisible(true);
	}

}
