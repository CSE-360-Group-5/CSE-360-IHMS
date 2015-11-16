///////////////////////////////////////////////////////////////////////////////
// Name: Registration.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: Content of the GUI for the registration of users to the IPIMS
// Date: 10/9/2015
///////////////////////////////////////////////////////////////////////////////

import java.sql.*;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Registration extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/////////////////////////////////////////////////////////////////
	// Labels
	/////////////////////////////////////////////////////////////////

	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel emailLabel;
	private JLabel passwordLabel;
	private JLabel dobLabel;
	private JLabel sexLabel;
	private JLabel marraigeLabel;
	private JLabel educationLabel;
	private JLabel raceLabel;
	private JLabel typeLabel;
	private JLabel registrationCodeLabel;
	private JLabel medicalHistoryLabel;
	
	private JLabel requiredLabel;
	private JLabel requiredLabel1;
	private JLabel requiredLabel2;
	private JLabel requiredLabel3;
	private JLabel requiredLabel4;
	
	/////////////////////////////////////////////////////////////////
	// Text fields
	/////////////////////////////////////////////////////////////////
	
	private JTextField firstNameField; // first name text field
	private JTextField lastNameField; // last name text field
	private JTextField emailField; // email address field
	private JTextField passwordField; // password field
	private JTextField registrationCodeField; // registration code field
	private JTextField medicalHistoryField; // medical history field
	private JTextField sexField; // gender field
	private JTextField marriageField; // Marriage field
	private JTextField educationField; //education field
	private JTextField raceField; // race field
	
	/////////////////////////////////////////////////////////////////
	// Radio Buttons
	/////////////////////////////////////////////////////////////////
	
	private JRadioButton male; //male option for sex radio button
	private JRadioButton female; //female option for sex radio button
	private ButtonGroup sexRadioButtons; //creates group of radio buttons and add them to the group
	
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
	
	private JRadioButton asian; //Asian option
	private JRadioButton latino; //Latino option
	private JRadioButton black; //Black option
	private JRadioButton caucasian; //Caucasian option
	private JRadioButton pacificIslander; //Pacific Islander
	private JRadioButton other; //other option
	private ButtonGroup raceButtons; //groups buttons together
	
	/////////////////////////////////////////////////////////////////
	// Combo boxes
	/////////////////////////////////////////////////////////////////
	
	private JComboBox monthDropdown; // DOB month dropdown
	private JComboBox dayDropdown; // DOB day dropdown
    private JComboBox yearDropdown; // DOB year dropdown
    private JComboBox typeDropdown; // user type dropdown
    
	/////////////////////////////////////////////////////////////////
	// Buttons
	/////////////////////////////////////////////////////////////////
	
	private JButton submitButton; // Submit button
	
	/////////////////////////////////////////////////////////////////
	// JPanels
	/////////////////////////////////////////////////////////////////
	
	private JPanel registrationCodePanel; // Registration Code panel
	private JPanel firstNamePanel; // First name panel
	private JPanel lastNamePanel; // Last name panel
	private JPanel emailPanel; // Email panel
	private JPanel passwordPanel; // Password panel
	private JPanel dobPanel; // User DOB panel
	private JPanel sexPanel; // gender panel
	private JPanel maritalPanel; // Marital status panel
	private JPanel educationPanel; // Education level panel
	private JPanel racePanel; // Race of user Panel
	private JPanel typePanel; // User type panel
	private JPanel submitPanel; // Submit button panel
	
	/////////////////////////////////////////////////////////////////
	// Database components
	/////////////////////////////////////////////////////////////////
	
	private Connection conn; // Connects to the database
	private Statement statement; // Create SQL queries
	private ResultSet rs; // Gets the result from SELECT commands
	
	public Registration()
	{
		
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
		
		// Instantiate the components
		
		//JLabels
		firstNameLabel = new JLabel("First name:"); // first name label
		lastNameLabel = new JLabel("Last name:"); // last name label
		emailLabel = new JLabel("Email address:"); // email address label
		passwordLabel = new JLabel("Password:"); // password label
		dobLabel = new JLabel("Date of Birth: "); // DOB label
		sexLabel = new JLabel("Sex: "); // sex Label
		marraigeLabel = new JLabel("Marital Status: "); // marital Status Label
		educationLabel = new JLabel ("Education: "); // education Label
		raceLabel = new JLabel ("Race: "); // race label
		typeLabel = new JLabel("Type:"); // type label
		registrationCodeLabel = new JLabel("Registration code:"); // registration code label
		medicalHistoryLabel = new JLabel("Medical History:"); // medical history label
		
		requiredLabel = new JLabel("* "); // required sign
		requiredLabel1 = new JLabel("* "); // required sign for first name label
		requiredLabel2 = new JLabel("* "); // required sign for last name label
		requiredLabel3 = new JLabel("* "); // required sign for email label
		requiredLabel4 = new JLabel("* "); // required sign for password label
		requiredLabel.setForeground(Color.red);
		requiredLabel1.setForeground(Color.red);
		requiredLabel2.setForeground(Color.red);
		requiredLabel3.setForeground(Color.red);
		requiredLabel4.setForeground(Color.red);
		
		//JTextFields
		firstNameField = new JTextField(15); // first name text field
		lastNameField = new JTextField(15); // last name text field
		emailField = new JTextField(15); // email address field
		passwordField = new JPasswordField(15); // password field
		registrationCodeField = new JTextField(15); // registration code field
		medicalHistoryField = new JTextField(15); // medical history field
		sexField = new JTextField(); // gender field
		marriageField = new JTextField(); // Marriage field
		educationField = new JTextField(); //education field
		raceField = new JTextField(); // race field
		
		//Radio Buttons
		male = new JRadioButton("Male"); //male option for sex radio button
		female = new JRadioButton("Female"); //female option for sex radio button
		sexRadioButtons = new ButtonGroup(); //creates group of radio buttons and add them to the group
		sexRadioButtons.add(male);
		sexRadioButtons.add(female);
		//add listener to radio buttons
		male.addActionListener(new SexListener());
		female.addActionListener(new SexListener());

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

		asian = new JRadioButton("Asian"); //Asian option
		latino = new JRadioButton("Latino"); //Latino option
		black = new JRadioButton("Black/African American"); //Black option
		caucasian = new JRadioButton ("Caucasian"); //Caucasian option
		pacificIslander = new JRadioButton ("Pacific Islander"); //Pacific Islander
		other = new JRadioButton("Other"); //other option
		raceButtons = new ButtonGroup(); //groups buttons together
		raceButtons.add(asian);
		raceButtons.add(latino);
		raceButtons.add(black);
		raceButtons.add(caucasian);
		raceButtons.add(pacificIslander);
		raceButtons.add(other);
		//add listener to radio buttons
		asian.addActionListener(new RaceListener());
		latino.addActionListener(new RaceListener());
		black.addActionListener(new RaceListener());
		caucasian.addActionListener(new RaceListener());
		pacificIslander.addActionListener(new RaceListener());
		other.addActionListener(new RaceListener());

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
	    
	    String[] type = {"---", "Patient", "Doctor"}; // types that person could be
		
		monthDropdown = new JComboBox(months); // DOB month dropdown
	    dayDropdown = new JComboBox(days); // DOB day dropdown
	    yearDropdown = new JComboBox(years); // DOB year dropdown
	    
	    typeDropdown = new JComboBox(type); // user type dropdown
	    typeDropdown.addActionListener(new ComboTypeListener()); // add Listener to combobox
	    
	    //Buttons
	    submitButton = new JButton("Submit"); // Submit button
	    submitButton.addActionListener(new ButtonListener()); //add listener
	    
	    //JPanels
	    firstNamePanel = new JPanel(); // First name panel
		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(requiredLabel1);
	    firstNamePanel.add(firstNameField);
		
		lastNamePanel = new JPanel(); // Last name panel
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(requiredLabel2);
	    lastNamePanel.add(lastNameField);
		
		emailPanel = new JPanel(); // Email panel
		emailPanel.add(emailLabel);
		emailPanel.add(requiredLabel3);
	    emailPanel.add(emailField);
		
		passwordPanel = new JPanel(); // Password panel
		passwordPanel.add(passwordLabel);
		passwordPanel.add(requiredLabel4);
	    passwordPanel.add(passwordField);
		
		dobPanel = new JPanel(); // User DOB panel
		dobPanel.add(dobLabel);
	    dobPanel.add(monthDropdown);
	    dobPanel.add(dayDropdown);
	    dobPanel.add(yearDropdown);
		
		sexPanel = new JPanel(); // gender panel
		sexPanel.add(sexLabel);
	    sexPanel.add(male);
	    sexPanel.add(female);
		
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
		
		racePanel = new JPanel(); //Race of user Panel
		racePanel.add(raceLabel);
	    racePanel.add(asian);
	    racePanel.add(latino);
	    racePanel.add(black);
	    racePanel.add(caucasian);
	    racePanel.add(pacificIslander);
	    racePanel.add(other);
		
		typePanel = new JPanel(); // User type panel
		typePanel.add(typeLabel);
	    typePanel.add(typeDropdown);
		
		registrationCodePanel = new JPanel(); // Registration Code panel
		
		submitPanel = new JPanel(); // Submit button panel
		submitPanel.add(submitButton);
		
		setLayout(new GridLayout(13,1)); // sets the layout and add components
		add(firstNamePanel);
	    add(lastNamePanel);
	    add(emailPanel);
	    add(passwordPanel);
	    add(dobPanel);
	    add(sexPanel);
	    add(maritalPanel);
	    add(educationPanel);
	    add(racePanel);
	    add(typePanel);
	    add(registrationCodePanel);
	    add(submitPanel);
	    
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setSize (750, 400);
	}
	
	//Listener to sex radio buttons 
	private class SexListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
        	Object select = event.getSource();
        	 
        	sexField.setText("Male");
	        if(select == male)
	        	sexField.setText("Male");
	        else if (select == female)
	        	sexField.setText("Female");
         }
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
	
	//Listener to race radio buttons 
	private class RaceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
        	Object source = event.getSource();
        	 
        	if(source == asian)
        		raceField.setText("Asian");
        	if(source == latino)
        		raceField.setText("Latino");
        	if(source == black)
        		raceField.setText("Black/African American");
        	if(source == caucasian)
        		raceField.setText("Caucasian");
        	if(source == pacificIslander)
        		raceField.setText("Pacific Islander");
        	else if(source == other)
        		raceField.setText("Other");
         }
     }
	
	private class ComboTypeListener implements ActionListener 
	{ 
		public void actionPerformed(ActionEvent event) 
		{ 
			if (typeDropdown.getSelectedIndex() == 0)
			{
				registrationCodePanel.removeAll();
				registrationCodePanel.revalidate();
				registrationCodePanel.repaint();
			}
			if (typeDropdown.getSelectedIndex() == 1)
			{
				registrationCodePanel.removeAll();
				medicalHistoryField.setText("Previous illnesses and current medication...");
				registrationCodePanel.setSize(15,15);
				registrationCodePanel.add(medicalHistoryLabel);
				registrationCodePanel.add(medicalHistoryField);
				registrationCodePanel.revalidate();
				registrationCodePanel.repaint();
			}
			if (typeDropdown.getSelectedIndex() >= 2)
			{
				registrationCodePanel.removeAll();
				registrationCodePanel.add(registrationCodeLabel);
				registrationCodePanel.add(requiredLabel);
				registrationCodePanel.add(registrationCodeField);
				registrationCodePanel.revalidate();
				registrationCodePanel.repaint();
			}
	    }
	}
	
	//Button Listener 
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			Object action = event.getSource();
			
			if(action == submitButton)
			{
				String type = (String) typeDropdown.getSelectedItem();
				String firstName = firstNameField.getText(); // first name of person
				String lastName = lastNameField.getText(); // last name of person
				String emailAddress = emailField.getText(); // email address of person
				String password = passwordField.getText(); // password of person
				int month = Integer.parseInt((String)monthDropdown.getSelectedItem()); // month of DOB
				int day = Integer.parseInt((String)dayDropdown.getSelectedItem()); // day of DOB
				int year = Integer.parseInt((String)yearDropdown.getSelectedItem()); // year of DOB
				String sex = sexField.getText(); //person's sex
				String mStatus = marriageField.getText(); //person's marital status
				String eLVL = educationField.getText(); // person's education lvl
				String pRace = raceField.getText(); //person's race
				String registrationCode = registrationCodeField.getText();
				String medicalHistory = "\n" + medicalHistoryField.getText();
				int id = 10000;
	
				String dob = "" + month + "/" + day + "/" + year;	
	
				/////////////////////////////////////////////////////////////////
				// Validation to check wrong input
				/////////////////////////////////////////////////////////////////
	
				if (typeDropdown.getSelectedIndex() == 0)
					JOptionPane.showMessageDialog(sexPanel, "Please select the type of user");
	
				// Check when an invalid date of birth is informed
				else if (year>2015 || ((month == 2 || month == 4 || month == 6 || month == 9 || month == 11) && day == 31))
					JOptionPane.showMessageDialog(sexPanel, "Please select a valid Date of Birth.");
	
				// Check when the registration code is not informed for required users
				else if (typeDropdown.getSelectedIndex() >= 2 && registrationCode.equals(""))
					JOptionPane.showMessageDialog(sexPanel, "Please fill all the required fields");
	
				else if (firstName.equals("") || lastName.equals("") || emailAddress.equals("") || password.equals(""))
					JOptionPane.showMessageDialog(sexPanel, "Please fill all the required fields");
				
				////////////////////////////////////////////////////////////////
				// Insert user in the database
				////////////////////////////////////////////////////////////////
								
				// Check when the user is patient and insert in the database
				else if (typeDropdown.getSelectedIndex() == 1)
				{
					try
					{
						statement = conn.createStatement();
						rs = statement.executeQuery("SELECT * FROM patient");
			            
			            while ( rs.next() ) {
			                id = rs.getInt(1);
			            }
						
			            id++;
						statement = conn.createStatement();
						statement.executeUpdate("INSERT INTO patient (idpatient, fname, lname, dob, email, password, sex, medHistory, mstatus, education, race, isNew)" + "VALUES (" + id + ", '" + firstName + "', '" + lastName + "', '" + dob + "', '" + emailAddress + "', '" + password + "', '" + sex + "', '" + medicalHistory + "', '" + mStatus + "', '" + eLVL + "', '" + pRace + "', 'true')");
						
						Patient patient = new Patient();
						patient.setFirstName(firstName);
						patient.setLastName(lastName);
						patient.setDOB(dob);
						patient.setSex(sex);
						patient.setEmail(emailAddress);
						patient.setPassword(password);
						patient.setPatientId(id);
						patient.setSocialData(mStatus, eLVL, pRace);
						patient.setIsNew("true");
						
						LoginPanel.registeredPatientList.add(patient);
						ViewRegisteredPatients.hasNew = true;
						
						JOptionPane.showMessageDialog(sexPanel, "User Registered!");
						dispose();
					}
					catch (Exception e)
					{
						System.err.println("Got an exception! "); 
			            System.err.println(e.getMessage()); 
			            System.err.println(e);
					}
					
				}
	
				// Check when the user is a staff member and insert them in the database
				else if (typeDropdown.getSelectedIndex() >= 2 && !registrationCode.equals(""))
				{
					try
					{
						statement = conn.createStatement();
						statement.executeUpdate("INSERT INTO staff (idstaff, staffType, fname, lname, dob, email, password, sex, mStatus, education, race)" + "VALUES (" + registrationCode + ", '"  + type + "', '" + firstName + "', '" + lastName + "', '" + dob + "', '" + emailAddress + "', '" + password + "', '" + sex + "', '" + mStatus + "', '" + eLVL + "', '" + pRace + "')");
					
						JOptionPane.showMessageDialog(sexPanel, "User Registered!");
						dispose();
					}
					catch (SQLIntegrityConstraintViolationException exp)
					{
						JOptionPane.showMessageDialog(sexPanel, "This registration code has already been registered!");
					}
					catch (Exception e)
					{
						System.err.println("Got an exception! ");
			            System.err.println(e.getMessage()); 
					}
				}
			}
		}
	} // end of ButtonListener

}
