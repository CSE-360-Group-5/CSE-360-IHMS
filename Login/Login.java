import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends JPanel
{
	
	private static final long serialVersionUID = 1L;

	private JPanel loginPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel typePanel;
	
	private JTextField emailAddress;
	private JPasswordField password;
	
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JLabel menuLabel;
	
	private JComboBox menu;
	
	private JButton enter;
	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	public Login(){
		
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
		
		emailLabel = new JLabel("Email: ");
		passwordLabel = new JLabel("Password: ");
		emailAddress = new JTextField(15);
		password = new JPasswordField(15);
		String[] types = {"Patient", "Doctor", "Lab Staff", "Pharmacist","Nurse"};
		menu = new JComboBox(types);
		menuLabel = new JLabel("Login Type: ");
		
		enter = new JButton("Enter");
		enter.addActionListener(new SubmitListener());
		
		usernamePanel = new JPanel();
		usernamePanel.add(emailLabel);
		usernamePanel.add(emailAddress);
		
		passwordPanel = new JPanel();
		passwordPanel.add(passwordLabel);
		passwordPanel.add(password);
		
		typePanel = new JPanel();
		typePanel.add(menuLabel);
		typePanel.add(menu);
		
		loginPanel = new JPanel();
		
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
		loginPanel.add(usernamePanel);
		loginPanel.add(passwordPanel);
		loginPanel.add(typePanel);
		loginPanel.add(enter);
		add(loginPanel);
	}
	
	private class SubmitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) 
        {
        	boolean access = false;
        	String type = (String) menu.getSelectedItem();
        	String query = "patient";
        	if(type.equals("Doctor"))
        		query = "staff";
        		
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
                		access = true;
                }
    		}
    		catch (Exception e)
    		{
    			System.err.println("Got an exception! "); 
                System.err.println(e.getMessage());
    		}
        	
        	if (access)
        		JOptionPane.showMessageDialog(typePanel, "Welcome!");
        	else
        		JOptionPane.showMessageDialog(typePanel, "User not registered!");
        }
        
    }
}
