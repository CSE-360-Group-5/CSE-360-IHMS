///////////////////////////////////////////////////////////////////////////////////////
// Name: LoginPanel.java
// Authors: Timothy Milea, Rian Martins
// Description: Create content of GUI for the login to the system
// Date: 11/6/2015
///////////////////////////////////////////////////////////////////////////////////////

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class LoginPanel extends JPanel
{
	
	private static final long serialVersionUID = 1L;
	
	public static Vector registeredPatientList;

	private JPanel loginPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel typePanel;
	private JPanel buttonPanel;
	
	private JFrame registrationFrame;
	
	private JTextField emailAddress;
	private JPasswordField password;
	
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JLabel menuLabel;
	
	private JComboBox menu;
	
	private JButton enter;
	private JButton register;
	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	
	public LoginPanel(){
		
		/////////////////////////////////////////////////////////////////
		// Database connection
		/////////////////////////////////////////////////////////////////
		
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
		
		/////////////////////////////////////////////////////////////////
		// Instantiate components
		/////////////////////////////////////////////////////////////////
		
		registeredPatientList = new Vector();
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM patient");
            
            while ( rs.next() ) 
            {
            	Patient obj = new Patient();
            	obj.setPatientId(rs.getInt("idpatient"));
            	obj.setFirstName(rs.getString("fname"));
            	obj.setLastName(rs.getString("lname"));
            	obj.setDOB(rs.getString("dob"));
            	obj.setEmail(rs.getString("email"));
            	obj.setPassword(rs.getString("password"));
            	obj.setSex(rs.getString("sex"));
            	obj.setSocialData(rs.getString("mStatus"), rs.getString("education"), rs.getString("race"));
            	obj.setIsNew(rs.getString("isNew"));
            	
            	registeredPatientList.add(obj);
            }
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
		}
		
		registrationFrame = new JFrame("Registration");
		
		emailLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		emailAddress = new JTextField(15);
		password = new JPasswordField(15);
		String[] types = {"Patient", "Doctor", "HSP" , "Lab Staff", "Pharmacist", "Nurse"};
		menu = new JComboBox(types);
		menuLabel = new JLabel("Login Type: ");
		
		enter = new JButton("Sign-in");
		register = new JButton("Sign-up");
		enter.addActionListener(new SubmitListener());
		register.addActionListener(new RegisterButtonListener());
		
		//add components to panel
		usernamePanel = new JPanel();
		usernamePanel.add(emailLabel);
		usernamePanel.add(emailAddress);
		
		passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(password);
		
		typePanel = new JPanel();
		typePanel.add(menuLabel);
		typePanel.add(menu);
		
		buttonPanel = new JPanel();
		buttonPanel.add(enter);
		buttonPanel.add(register);
		
		loginPanel = new JPanel();
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(usernamePanel);
		loginPanel.add(passwordPanel);
		loginPanel.add(typePanel);
		loginPanel.add(buttonPanel);
		
		add(loginPanel);
		setLocation(10,10);
	}
	
	private class SubmitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) 
        {
        	boolean access = false;
        	String type = (String) menu.getSelectedItem();
        	String query = "staff";
        	if(type.equals("Patient"))
        		query = "patient";
        	int id=0;
        	boolean wrongType = false;
        	
        	try
    		{
    			statement = conn.createStatement();
    			rs = statement.executeQuery("SELECT * FROM "+ query);
                
                while ( rs.next() ) 
                {
                	String pass = rs.getString("password");
                	String passwordTyped = String.valueOf(password.getPassword());
                	String login = rs.getString("email");
                	
                	if(passwordTyped.equals(pass) && login.equals(emailAddress.getText()))
                	{
                		if(type.equals("Patient") || type.equals("Doctor"))
                		{
                			access = true;
                    		id = rs.getInt(1);
                		}
                		else if(!emailAddress.getText().equalsIgnoreCase(type) && !emailAddress.getText().equals("labstaff")){
                			wrongType = true;
                			JOptionPane.showMessageDialog(typePanel, "Please select your correct type");
                		}
                		else if(emailAddress.getText().equals("labstaff") && !type.equals("Lab Staff")){
                			wrongType = true;
                			JOptionPane.showMessageDialog(typePanel, "Please select your correct type");
                		}
                		else
                		{
                			access = true;
                    		id = rs.getInt(1);
                		}
                	}
                }
    		}
    		catch (Exception e)
    		{
    			System.err.println("Got an exception! "); 
                System.err.println(e.getMessage());
    		}
        	
        	if (access)
        	{
        		Profile p1 = new Profile(query, id, type);
        		p1.setVisible(true);
        		emailAddress.setText("");
        		password.setText("");
        	}
        	else if(!wrongType)
        		JOptionPane.showMessageDialog(typePanel, "User not registered!");
        }
        
    }
	
	private class RegisterButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) 
        {
        	Registration registPanel = new Registration();
        	registrationFrame = registPanel;
        	registrationFrame.setVisible(true);
        	
        }
        
    }
}
