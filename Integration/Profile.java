///////////////////////////////////////////////////////////////////////////////////////
// Name: Profile.java
// Authors: Rian Martins
// Description: Create content of GUI for profile page for all the users of the IPIMS
// Date: 11/7/2015
///////////////////////////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Profile extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	//////////////////////////////////////////////////////////////////
	// Menu
	//////////////////////////////////////////////////////////////////
	
	private JMenuBar menu;
	private JMenu menuOp1;
	private JMenu menuOp2;
	private JMenu menuOp3;
	private JMenu menuOp4;
	private JMenu menuOp5;
	private JMenu menuOp6;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	
	//////////////////////////////////////////////////////////////////
	// JLabels
	//////////////////////////////////////////////////////////////////
	
	private JLabel greetingLabel;
	private JLabel appointmentListLabel;
	private JLabel updateImageLabel;
	private JLabel fullNameLabel;
	private JLabel ageLabel;
	private JLabel sexLabel;
	private JLabel logout;
	
	//////////////////////////////////////////////////////////////////
	// Other variables
	//////////////////////////////////////////////////////////////////
	
	private String fullName;
	private String firstName;
	private String lastName;
	private String dob;
	private String sex;
	private int age;
	
	//////////////////////////////////////////////////////////////////
	// JTextArea
	//////////////////////////////////////////////////////////////////
	
	private JTextArea appointmentListArea;
	
	//////////////////////////////////////////////////////////////////
	// Vector
	//////////////////////////////////////////////////////////////////
	
	private Vector appointmentList;
	
	//////////////////////////////////////////////////////////////////
	// JPanels
	//////////////////////////////////////////////////////////////////
	
	private JPanel userInfoPanel;
	private JPanel appointmentPanel;
	private JPanel centerWest;
	
	//////////////////////////////////////////////////////////////////
	// Database components
	//////////////////////////////////////////////////////////////////
	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	//////////////////////////////////////////////////////////////////
	// static variables
	//////////////////////////////////////////////////////////////////
	
	static String hcr="";
	
	public Profile(String database, final int id, final String type)
	{
		
		//////////////////////////////////////////////////////////////////
		// Database connection
		//////////////////////////////////////////////////////////////////
		
		try
		{
			// "Load" the JDBC driver
			Class.forName("java.sql.Driver");
		
			// Establish the connection to the database
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url,"root","admin");
		}
		
		catch (Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
		
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient` = " + id);
			
			while(rs.next())
			{
				hcr = rs.getString("HCR");
			}
			
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM staff WHERE `idstaff` = " + id);
			
			while(rs.next())
			{
				fullName = rs.getString("fname") + " " + rs.getString("lname");
			}
		}
		catch (SQLException e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
		//////////////////////////////////////////////////////////////////
		// Instantiate components
		//////////////////////////////////////////////////////////////////
		
		//MENU ACTIONS
		
		 // Action to make/view appointments
		class AppointmentAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public AppointmentAction() {
		        putValue(SHORT_DESCRIPTION, "Create new appointments");
		    }
		    public void actionPerformed(ActionEvent e) {
		    	if(hcr.equals("empty"))
		    		JOptionPane.showMessageDialog(centerWest, "Sorry, you don't have Healthcare Report uploaded, please contact the HSP");
		    	else
		    	{
		    		MakeAppointments appointFrame = new MakeAppointments(id);
		    		appointFrame.setVisible(true);
		    	}
		    }
		}
		Action appAction = new AppointmentAction();
		
		// Action to view new patient registered
		class NewPatientAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public NewPatientAction() {
		        putValue(SHORT_DESCRIPTION, "View list of new patients");
		    }
		    public void actionPerformed(ActionEvent e) {
		    	ViewRegisteredPatients vp = new ViewRegisteredPatients("new");
		    	vp.setVisible(true);
		    	ViewRegisteredPatients.hasNew = false;
		    }
		}
		Action newPatientAction = new NewPatientAction();
		
		// Action to view all patient registered
		class AllPatientAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public AllPatientAction() {
		        putValue(SHORT_DESCRIPTION, "View list of all patients");
		    }
		    public void actionPerformed(ActionEvent e) {
		    	ViewRegisteredPatients vp = new ViewRegisteredPatients("all");
		    	vp.setVisible(true);
		    }
		}
		Action allPatientAction = new AllPatientAction();
		
		// Action to view all patient registered
		class SearchPatientAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public SearchPatientAction() {
				putValue(SHORT_DESCRIPTION, "View list of all patients");
			}
			public void actionPerformed(ActionEvent e) {
				SearchPatient sp = new SearchPatient(type, id);
				sp.setVisible(true);
				dispose();
			}
		}
		Action searchPatientAction = new SearchPatientAction();
		
		// Action to open Statistical Report
		class StatsReportAction implements MenuListener
		{
		    public void menuSelected(MenuEvent e) {
		        StatsReport report = new StatsReport();
		        report.setVisible(true);
		        report.setAlwaysOnTop(true);
		    }
		    public void menuDeselected(MenuEvent e){}
		    public void menuCanceled(MenuEvent e){}
		}
		
		// Action to open Statistical Report
		class FinalizeAppAction implements MenuListener
		{
			public void menuSelected(MenuEvent e) {
				FinalizeAppointment finApp = new FinalizeAppointment(type, fullName);
				finApp.setVisible(true);
				finApp.setAlwaysOnTop(true);
			}
			public void menuDeselected(MenuEvent e){}
			public void menuCanceled(MenuEvent e){}
		}
		
		 // Action to open lab record
		class LabRecAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public LabRecAction() {
		        putValue(SHORT_DESCRIPTION, "Create new lab records");
		    }
		    public void actionPerformed(ActionEvent e) {
		    	dispose();
		    	LabRecords labRec = new LabRecords();
		    	labRec.setVisible(true);
		    }
		}
		Action labAction = new LabRecAction();
		
		// MENU COMPONENTS
		menu = new JMenuBar();
		
		menuOp1 = new JMenu();
		menuOp2 = new JMenu();
		menuOp3 = new JMenu();
		menuOp4 = new JMenu();
		menuOp5 = new JMenu();
		
		if(type.equals("Patient")) //if a patient is logging in
		{
			menuOp1.setText("Profile");
			menuOp2.setText("Manage Appoitment");
			menuOp3.setText("Healthcare Condition");
			
			menuItem1 = new JMenuItem(appAction);
			menuItem1.setText("Make/Cancel Appoitments");
			menuItem2 = new JMenuItem("Update Healthcare Condition");
			
			menuOp2.add(menuItem1);
			menuOp3.add(menuItem2);
			
			menu.add(menuOp1);
			menu.add(menuOp2);
			menu.add(menuOp3);
		}
		else if (type.equals("Doctor")) //if a doctor is logging in
		{
			menuOp1.setText("Profile");
			menuOp2.setText("Patients");
			menuOp3.setText("Appointments Request");
			menuOp3.addMenuListener(new FinalizeAppAction());
			menuOp4.setText("View Medical Alerts");
			
			menuItem1 = new JMenuItem(searchPatientAction);
			menuItem1.setText("Search Patient");
			
			menuOp2.add(menuItem1);
			
			menu.add(menuOp1);
			menu.add(menuOp2);
			menu.add(menuOp3);
			menu.add(menuOp4);
		}
		else if (type.equals("HSP")) //if the HSP is logging in
		{
			menuOp1.setText("Profile");
			menuOp2.setText("Patients");
			menuOp3.setText("Appointment Request");
			menuOp3.addMenuListener(new FinalizeAppAction());
			menuOp4.setText("View Medical Alerts");
			menuOp5.setText("Generate Statistical Report");
			menuOp5.addMenuListener(new StatsReportAction());
			
			menuItem1 = new JMenuItem(searchPatientAction);
			menuItem1.setText("Search Patient");
			menuOp6 = new JMenu("List of Registered Patient");
			menuOp6.setMnemonic(KeyEvent.VK_S);
			
			menuItem2 = new JMenuItem(newPatientAction);
			menuItem2.setText("List of New Registered Patient");
			menuItem3 = new JMenuItem(allPatientAction);
			menuItem3.setText("List of All Registered Patient");
			
			menuOp2.add(menuItem1);
			menuOp2.add(menuOp6);
			menuOp6.add(menuItem2);
			menuOp6.add(menuItem3);
			
			menu.add(menuOp1);
			menu.add(menuOp2);
			menu.add(menuOp3);
			menu.add(menuOp4);
			menu.add(menuOp5);
			
			if(ViewRegisteredPatients.hasNew)
			{
				JOptionPane.showMessageDialog(centerWest, "There is/are new patient(s) registered");
			}
		}
		else if (type.equals("Lab Staff")) //if the labstaff is logging in
		{
			menuOp1.setText("Profile");
			menuOp2.setText("Lab Records");
			
			menuItem1 = new JMenuItem(labAction);
			menuItem1.setText("Upload/Update Lab Records");
			menuOp2.add(menuItem1);
			
			menu.add(menuOp1);
			menu.add(menuOp2);
		}
		else if (type.equals("Pharmacist")) //if the Pharmacist is logging in
		{
			menuOp1.setText("Profile");
			menuOp2.setText("Patients");
			
			menuItem1 = new JMenuItem(searchPatientAction);
			menuItem1.setText("Search Patient");
			
			menuOp2.add(menuItem1);
			
			menu.add(menuOp1);
			menu.add(menuOp2);
		}
		else if (type.equals("Nurse")) //if the nurse is logging in
		{
			menuOp1.setText("Profile");
			menuOp2.setText("Patients");
			
			menuItem1 = new JMenuItem(searchPatientAction);
			menuItem1.setText("Search Patient");
			
			menuOp2.add(menuItem1);
			
			menu.add(menuOp1);
			menu.add(menuOp2);
		}
		
		// JLABELS
		greetingLabel = new JLabel("Welcome, ");
		greetingLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		
		appointmentListLabel = new JLabel("List of Appointments:");
		
		fullNameLabel = new JLabel("Name: ");
		ageLabel = new JLabel("Age: ");
		sexLabel = new JLabel("Sex: ");
		
		updateImageLabel = new JLabel();
		updateImageLabel.setText("<HTML><U>Update Profile Image</U></HTML>");
		updateImageLabel.setForeground(Color.blue);
		
		logout = new JLabel("Logout", SwingConstants.RIGHT);
		logout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logout.addMouseListener(new MouseLogoutListener());
		
		// JTEXTAREAS
		appointmentListArea = new JTextArea(15, 10);
		appointmentListArea.setText("No appointments approved.");
		appointmentListArea.setEditable(false);
		
		// VECTOR
		appointmentList = new Vector();
		
		// DATABASE SELECT TO GET USER INFORMATION
		try
		{
			String colName = "idpatient";
			if(database.equals("staff"))
				colName = "idstaff";
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM " + database + " WHERE " + colName + " = '" + id + "';");
            
            while ( rs.next() )
            {
                firstName = rs.getString("fname");
                lastName = rs.getString("lname");
                dob = rs.getString("dob");
                sex = rs.getString("sex");
            }
			
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
		}
		
		// Insert user information
		greetingLabel.setText(greetingLabel.getText() + firstName);
		fullNameLabel.setText(fullNameLabel.getText() + firstName + " " + lastName);
		dob = dob.substring(dob.length()-4);
		age = Integer.parseInt(dob);
		age = 2015 - age;
		ageLabel.setText(ageLabel.getText() + age);
		sexLabel.setText(sexLabel.getText() + sex);
		
		// DATABASE SELECT TO GET APPOINTMENT LIST
		boolean hasAppointment = false;
		if(type.equals("Patient"))
		{
			try
			{
				statement = conn.createStatement();
				rs = statement.executeQuery("SELECT * FROM appointments WHERE `patientID` = " + id);
				if(rs.next() && rs.getString("finalized").equals("false"))
				{
					appointmentListArea.setText("");
					rs.previous();
				}
				while(rs.next())
				{
					Appointment a1 = new Appointment(rs.getString("docName"),rs.getString("month"),rs.getString("year"),rs.getString("day"),rs.getString("time"),rs.getInt("row"),rs.getInt("col"),rs.getInt("patientID"),rs.getInt("appointmentsID"));
					if(rs.getString("finalized").equals("true") || !rs.getString("status").equals("approved")){}
					else
					{
						hasAppointment = true;
						appointmentList.add(a1);
						appointmentListArea.append(a1.toStringPatient());
					}
				}
				if(!hasAppointment)
					appointmentListArea.setText("No appointments approved.");
			}
			catch (SQLException e)
			{
				System.err.println("Got an exception!");
				System.err.println(e.getMessage());
			}
		}
		else if (type.equals("Doctor"))
		{
			try
			{
				statement = conn.createStatement();
				rs = statement.executeQuery("SELECT * FROM appointments WHERE `docName` = '" + fullName + "';");
				if(rs.next() || rs.getString("finalized").equals("false"))
				{
					appointmentListArea.setText("");
					rs.previous();
				}
				while(rs.next())
				{
					Appointment a1 = new Appointment(rs.getString("docName"),rs.getString("month"),rs.getString("year"),rs.getString("day"),rs.getString("time"),rs.getInt("row"),rs.getInt("col"),rs.getInt("patientID"),rs.getInt("appointmentsID"));
					if(rs.getString("finalized").equals("true") || !rs.getString("status").equals("approved")){}
					else
					{
						hasAppointment = true;
						appointmentList.add(a1);
						appointmentListArea.append(a1.toStringDoctor());
					}
				}
				if(!hasAppointment)
					appointmentListArea.setText("No appointments approved.");
			}
			catch (SQLException e)
			{
				System.err.println("Got an exception!");
				System.err.println(e.getMessage());
			}
		}
		
		//ADD COMPONENTS TO JPANELS		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		
		userInfoPanel = new JPanel();
		userInfoPanel.setBorder(padding);
		userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
		userInfoPanel.add(logout);
		userInfoPanel.add(Box.createRigidArea(new Dimension(0,135)));
		userInfoPanel.add(fullNameLabel);
		userInfoPanel.add(ageLabel);
		userInfoPanel.add(sexLabel);
		
		appointmentPanel = new JPanel();
		appointmentPanel.setLayout(new BoxLayout(appointmentPanel, BoxLayout.Y_AXIS));
		appointmentPanel.add(appointmentListLabel);
		appointmentPanel.add(appointmentListArea);
		
		centerWest = new JPanel();
		centerWest.setBorder(padding);
		centerWest.setLayout(new BoxLayout(centerWest, BoxLayout.Y_AXIS));
		centerWest.add(greetingLabel);
		centerWest.add(Box.createRigidArea(new Dimension(0,100)));
		centerWest.add(appointmentPanel);
		
		setJMenuBar(menu);
		setLayout(new BorderLayout()); // set layout
		add(centerWest, BorderLayout.WEST);
		add(userInfoPanel, BorderLayout.EAST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation
		setSize(1200,580); // set size of window
		setTitle("IPIMS - Profile"); // set tilte of frame
	}
	
	private class MouseLogoutListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent e)
		{
			dispose();
		}
		public void mouseEntered(MouseEvent arg0){}
		public void mouseExited(MouseEvent arg0){}
		public void mousePressed(MouseEvent arg0){}
		public void mouseReleased(MouseEvent arg0){}
	}
}
