/* Name: editButton.java
 * Author: Zarif
 * Description: Will allow for patients to update their profile 
 * Date: 11/16/2015
 */
import java.sql.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
public class editButton extends JFrame {
	
	private static final long serialVersionUID = 1L;

	// LABEL
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel passwordLabel;
	private JLabel marraigeLabel;
	private JLabel educationLabel;

	// TEXTFIELDS
	private JLabel firstNameField; // first name text field
	private JLabel lastNameField; // last name text field
	private JPasswordField passwordField; // password field
	private JTextField marriageField; // Marriage field
	private JTextField educationField; //education field
	
	// Radio Buttons
	private JRadioButton single; //single option
	private JRadioButton married; //divorced option
	private ButtonGroup marriageButtons; //groups buttons together
	
	private JRadioButton noDegree; //no option
	private JRadioButton highSchool; //high school option
	private JRadioButton someCollege; //some college option
	private JRadioButton bachelors; //bachelor option
	private JRadioButton masters; //masters option
	private JRadioButton doctorates; //doctorates option
	private ButtonGroup degreeButtons; //groups buttons together
	
	
	// Buttons 
	private JButton updateButton; // updates database with new inputs 
	private JButton cancelButton; // cancels the update process
	
	// JPanels 
	private JPanel firstNamePanel; // First name panel
	private JPanel lastNamePanel; // Last name panel
	private JPanel passwordPanel; // Password panel
	private JPanel maritalPanel; // Marital status panel
	private JPanel educationPanel; // Education level panel
	private JPanel updatePanel; // Update panel
	private JPanel cancelPanel; // cancel button panel 
	
	// JComboBox
	private JComboBox monthDropdown; // DOB month dropdown
	private JComboBox dayDropdown; // DOB day dropdown
    private JComboBox yearDropdown; // DOB year dropdown
    private JComboBox typeDropdown; // user type dropdown
	
	// Database Components 
	private Connection conn; // Connects to the database
	private Statement statement; // Create SQL queries
	private ResultSet rs; // Gets the result from SELECT commands
	private ResultSet rs1;
	int id1;
	public editButton(int id) {	
		// DataBase Connection 
		id1 = id;
		try
		{
			// "Load" the JDBC driver
			Class.forName("java.sql.Driver");

			// Establish the connection to the database
			String url = "jdbc:mysql://localhost:3306/cse360";
			conn = DriverManager.getConnection(url,"root","Nikitha7!");
		}

		catch (Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
	    }
		firstNameField = new JLabel();
		lastNameField = new JLabel();
		passwordField = new JPasswordField(20);

		// JLabels
		firstNameLabel = new JLabel("First name:"); // first name label
		lastNameLabel = new JLabel("Last name:"); // last name label
		passwordLabel = new JLabel("Password:"); // password label
		marraigeLabel = new JLabel("Marital Status: "); // marital Status Label
		educationLabel = new JLabel ("Education: "); // education Label
		
		//JTextFields
		//firstNameField = new JLabel(); // first name text field
		//lastNameField = new JLabel(); // last name text field
		//passwordField = new JPasswordField(15); // password field
		marriageField = new JTextField(); // Marriage field
		educationField = new JTextField(); //education field

		single = new JRadioButton("Single"); //single option
		married = new JRadioButton("Married"); //divorced option
		marriageButtons = new ButtonGroup(); //groups buttons together
		marriageButtons.add(single);
		marriageButtons.add(married);
		//add listener to radio buttons
		single.addActionListener(new MaritalStatusListener());
		married.addActionListener(new MaritalStatusListener());

		noDegree = new JRadioButton("No Degree"); //no option
		highSchool = new JRadioButton("High School or Equivalent"); //high school option
		someCollege = new JRadioButton("Some College"); //some college option
		bachelors = new JRadioButton ("Bachelors"); //bachelor option
		masters = new JRadioButton ("Masters"); //masters option
		doctorates = new JRadioButton("Doctorates"); //doctorates option
		degreeButtons = new ButtonGroup(); //groups buttons together
		degreeButtons.add(noDegree);
		degreeButtons.add(highSchool);
		degreeButtons.add(someCollege);
		degreeButtons.add(bachelors);
		degreeButtons.add(masters);
		degreeButtons.add(doctorates);
		//add listener to radio buttons
		noDegree.addActionListener(new EducationListener());
		highSchool.addActionListener(new EducationListener());
		someCollege.addActionListener(new EducationListener());
		bachelors.addActionListener(new EducationListener());
		masters.addActionListener(new EducationListener());
		doctorates.addActionListener(new EducationListener());
		
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient` = '" + id + "';");
			while(rs.next())
			{
				firstNameField.setText(rs.getString("fname"));
				lastNameField.setText(rs.getString ("lname"));
				passwordField.setText(rs.getString("password"));
				if(rs.getString("education").equals("No Degree"))
	        		noDegree.setSelected(true);
				if(rs.getString("education").equals("High School or Equivalent"))
	        		highSchool.setSelected(true);
				if(rs.getString("education").equals("Some College"))
	        		someCollege.setSelected(true);
				if(rs.getString("education").equals("Bachelors"))
	        		bachelors.setSelected(true);
				if(rs.getString("education").equals("Masters"))
	        		masters.setSelected(true);
				if(rs.getString("education").equals("Doctorates"))
	        		doctorates.setSelected(true);
				if(rs.getString("mstatus").equals("Single"))
					single.setSelected(true);
				if(rs.getString("mstatus").equals("Married"))
					married.setSelected(true);
			}
			
        }
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
            System.err.println(e);
		}
		
		//Combo boxes
		String[] months = new String[12]; // months array (1-12)
	    for (int i = 0; i < 12; i++) // fill the array automatically
	      months[i] = Integer.toString(i+1);

	    String[] days = new String[31]; // days array (1-31)
	    for (int i = 0; i < 31; i++) // fill the array automatically
	      days[i] = Integer.toString(i+1);

	    String[] years = new String[125]; // years array (1900-2025)
	    for (int i = 0; i < 125; i++) // fill the array automatically
	      years[i] = Integer.toString(2025-i);
	    
	    //Buttons
	    updateButton = new JButton("Update"); // Submit button
	    updateButton.addActionListener(new ButtonListener()); //add listener
	    
	    cancelButton = new JButton("Cancel"); // Edit Button
	    cancelButton.addActionListener(new ButtonListener());
	    
	  //JPanels
	    firstNamePanel = new JPanel(); // First name panel
		firstNamePanel.add(firstNameLabel);
	    firstNamePanel.add(firstNameField);
		
		lastNamePanel = new JPanel(); // Last name panel
		lastNamePanel.add(lastNameLabel);
	    lastNamePanel.add(lastNameField);
		
		passwordPanel = new JPanel(); // Password panel
		passwordPanel.add(passwordLabel);
	    passwordPanel.add(passwordField);

		maritalPanel = new JPanel(); //marital status panel
		maritalPanel.add(marraigeLabel);
	    maritalPanel.add(marraigeLabel);
	    maritalPanel.add(single);
	    maritalPanel.add(married);
		
		educationPanel = new JPanel(); //education level panel
		educationPanel.add(educationLabel);
	    educationPanel.add(noDegree);
	    educationPanel.add(highSchool);
	    educationPanel.add(someCollege);
	    educationPanel.add(bachelors);
	    educationPanel.add(masters);
	    educationPanel.add(doctorates);
		
		updatePanel = new JPanel(); // Submit button panel
		updatePanel.add(updateButton);
		
		cancelPanel = new JPanel();
		cancelPanel.add(cancelButton);
		
		setLayout(new GridLayout(12,1)); // sets the layout and add components
		add(firstNamePanel);
	    add(lastNamePanel);
	    add(passwordPanel);
	    add(maritalPanel);
	    add(educationPanel);
	    add(updatePanel);
	    add(cancelPanel);
	    
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setSize (750, 400);
				
	}
	
	//Listener to marital status radio buttons 
	private class MaritalStatusListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
        	Object select = event.getSource();
        	 
        	marriageField.setText("Single");
        	if(select == single)
        		marriageField.setText("Single");
        	else if (select == married)
        		marriageField.setText("Married");
         }
     }
	
	//Listener to education radio buttons 
	private class EducationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
        	Object source = event.getSource();
        	 
        	if(source == noDegree)
        		educationField.setText("No Degree");
        	if(source == highSchool)
        		educationField.setText("High School or Equivalent");
        	if(source == someCollege)
        		educationField.setText("College Degree");
        	if(source == bachelors)
        		educationField.setText("Bachelors");
        	if(source == masters)
        		educationField.setText("Masters");
        	else if(source == doctorates)
        		educationField.setText("Doctorates");
         }
     }
		
	//Button Listener 
		private class ButtonListener implements ActionListener
		{
			public void actionPerformed (ActionEvent event)
			{				
				Object action = event.getSource();
				if(action == updateButton)
				{					
					if(action == updateButton)
					{
						//String type = (String) typeDropdown.getSelectedItem();
						//String firstName = firstNameField.getText(); // first name of person
						//String lastName = lastNameField.getText(); // last name of person
						String password = passwordField.getText(); // password of person
						String mStatus = "";
						String eLVL = "";
						
						if(noDegree.isSelected())
			        		eLVL = "No Degree";
			        	if(highSchool.isSelected())
			        		eLVL = "High School or Equivalent";
			        	if(someCollege.isSelected())
			        		eLVL = "College Degree";
			        	if(bachelors.isSelected())
			        		eLVL = "Bachelors";
			        	if(masters.isSelected())
			        		eLVL = "Masters";
			        	else if(doctorates.isSelected())
			        		eLVL = "Doctorates";
			        	
			        	if(single.isSelected())
			        		mStatus = "Single";
			        	if(married.isSelected())
			        		mStatus = "Married";
						
						//mStatus = marriageField.getText(); //person's marital status
						//eLVL = educationField.getText(); // person's education lvl
						//int id = 10000;
						
						/////////////////////////////////////////////////////////////////
						// Validation to check wrong input
						/////////////////////////////////////////////////////////////////
						if (password.equals(""))
							JOptionPane.showMessageDialog(educationPanel, "Please fill all the required fields");
						
						////////////////////////////////////////////////////////////////
						// Insert user in the database
						////////////////////////////////////////////////////////////////
										
						// Check when the user is patient and insert in the database
						//else if (typeDropdown.getSelectedIndex() == 1)
						//{
						//	try
						//{
								//statement = conn.createStatement();
								//rs = statement.executeQuery("SELECT * FROM patient");
					            
					            //while ( rs.next() ) {
					            //    id = rs.getInt(1);
					            //}
								
					            //id++;
								try {
									statement = conn.createStatement();
									statement.execute("UPDATE patient SET password='" + password + "', mstatus ='" + mStatus + "', education ='" + eLVL + "' WHERE `idpatient` = '" + id1 + "';");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								//Patient patient = new Patient();
								//patient.setFirstName(firstName);
								//patient.setLastName(lastName);
								//patient.setPassword(password);
								//patient.setSocialData(firstName, mStatus, eLVL);
/*								patient.setIsNew("true");
								
								LoginPanel.registeredPatientList.add(patient);
								ViewRegisteredPatients.hasNew = true;*/
								
//								JOptionPane.showMessageDialog(sexPanel, "User Registered!");
							//	dispose();
							//}
							//catch (Exception e)
							//{
							//	System.err.println("Got an exception! "); 
					        //    System.err.println(e.getMessage()); 
					        //    System.err.println(e);
							//}
							
						//}
					}	
				}
				
				if (action == cancelButton)
				{
					dispose();
				}
			}
		} // end of ButtonListener
}