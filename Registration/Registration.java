//////////////////////////////////////////////////////////////////
// Name: Registration.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the registration of users to the IPIMS
// Date: 10/9/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Registration {
  /////////////////////////////////////////////////////////////////
  // Main class
  /////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    /////////////////////////////////////////////////////////////////
    // Labels
    /////////////////////////////////////////////////////////////////

    JLabel firstNameLabel = new JLabel("First name:"); // first name label
    JLabel lastNameLabel = new JLabel("Last name:"); // last name label
    JLabel emailLabel = new JLabel("Email address:"); // email address label
    JLabel passwordLabel = new JLabel("Password:"); // password label
    JLabel dobLabel = new JLabel("Date of Birth: "); // DOB label
    JLabel sexLabel = new JLabel("Sex: ");
    JLabel typeLabel = new JLabel("Type:"); // type label
    JLabel registrationCodeLabel = new JLabel("Registration code:"); // registration code label
    JLabel medicalHistoryLabel = new JLabel("Medical History:");
    JLabel requiredLabel = new JLabel("* ");
    requiredLabel.setForeground(Color.red);

    /////////////////////////////////////////////////////////////////
    // Text fields
    /////////////////////////////////////////////////////////////////

    JTextField firstNameField = new JTextField(15); // first name text field
    JTextField lastNameField = new JTextField(15); // last name text field
    JTextField emailField = new JTextField(15); // email address field
    JTextField passwordField = new JPasswordField(15); // password field
    JTextField registrationCodeField = new JTextField(15); // registration code field
    JTextField medicalHistoryField = new JTextField(15); // medical history field
    JTextField sexField = new JTextField(); // gender field

	/////////////////////////////////////////////////////////////////
	// Radio buttons
    /////////////////////////////////////////////////////////////////

	JRadioButton male = new JRadioButton("Male", true); //male option for sex radio button
	JRadioButton female = new JRadioButton("Female"); //female option for sex radio button
	ButtonGroup sexRadioButtons = new ButtonGroup(); //creates group of radio buttons and add them to the group
	sexRadioButtons.add(male);
	sexRadioButtons.add(female);

	class ChoiceListener implements ActionListener
	{
	        //  Sets the text of the field depending on which
	        // radio button was pressed.
	        public void actionPerformed (ActionEvent event)
	        {
	           Object source = event.getSource();

	        	if(source == male)
					sexField.setText("Male");
				else if (source == female)
					sexField.setText("Female");
			}
    }

	ChoiceListener listener = new ChoiceListener();
	male.addActionListener(listener);
	female.addActionListener(listener);

    /////////////////////////////////////////////////////////////////
    // Combo boxes
    /////////////////////////////////////////////////////////////////

    String[] months = new String[12]; // months array (1-12)
    for (int i = 0; i < 12; i++) // fill the array automatically
      months[i] = Integer.toString(i+1);

    String[] days = new String[31]; // days array (1-31)
    for (int i = 0; i < 31; i++) // fill the array automatically
      days[i] = Integer.toString(i+1);

    String[] years = new String[125]; // years array (1900-2025)
    for (int i = 0; i < 125; i++) // fill the array automatically
      years[i] = Integer.toString(2025-i);

    JComboBox monthDropdown = new JComboBox<String>(months); // DOB month dropdown
    JComboBox dayDropdown = new JComboBox<String>(days); // DOB day dropdown
    JComboBox yearDropdown = new JComboBox<String>(years); // DOB year dropdown

    String[] type = {"---", "Patient", "Doctor", "Nurse", "Healthcare Provider", "Lab Staff", "Pharmacist"}; // types that person could be
	JComboBox typeDropdown = new JComboBox<String>(type); // user type dropdown

    /////////////////////////////////////////////////////////////////
    // Buttons
    /////////////////////////////////////////////////////////////////

    JButton submitButton = new JButton("Submit"); // Submit button

    /////////////////////////////////////////////////////////////////
    // Frames and panels
    /////////////////////////////////////////////////////////////////

    JFrame frame = new JFrame("Registraton");

    JPanel registrationCodePanel = new JPanel(); // Registration Code panel

	typeDropdown.addActionListener(
		new ActionListener(){
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
	);

    JPanel firstNamePanel = new JPanel(); // First name panel
    firstNamePanel.add(firstNameLabel);
    firstNamePanel.add(firstNameField);

    JPanel lastNamePanel = new JPanel(); // Last name panel
    lastNamePanel.add(lastNameLabel);
    lastNamePanel.add(lastNameField);

    JPanel emailPanel = new JPanel(); // Email panel
    emailPanel.add(emailLabel);
    emailPanel.add(emailField);

    JPanel passwordPanel = new JPanel(); // Password panel
    passwordPanel.add(passwordLabel);
    passwordPanel.add(passwordField);

    JPanel dobPanel = new JPanel(); // User DOB panel
    dobPanel.add(dobLabel);
    dobPanel.add(monthDropdown);
    dobPanel.add(dayDropdown);
    dobPanel.add(yearDropdown);

    JPanel sexPanel = new JPanel();
    sexPanel.add(sexLabel);
    sexPanel.add(male);
    sexPanel.add(female);

    JPanel typePanel = new JPanel(); // User type panel
    typePanel.add(typeLabel);
    typePanel.add(typeDropdown);

    JPanel submitPanel = new JPanel(); // Submit button panel
    submitPanel.add(submitButton);

    // Add panels to container
    Container content = frame.getContentPane();
    content.setLayout(new GridLayout(9,1));
    content.add(firstNamePanel);
    content.add(lastNamePanel);
    content.add(emailPanel);
    content.add(passwordPanel);
    content.add(dobPanel);
    content.add(sexPanel);
    content.add(typePanel);
    content.add(registrationCodePanel);
    content.add(submitPanel);

    // Submit button listener
    submitButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
			/////////////////////////////////////////////////////////////////
			// data variables for the user
			/////////////////////////////////////////////////////////////////
			String firstName = firstNameField.getText(); // first name of person
			String lastName = lastNameField.getText(); // last name of person
			String emailAddress = emailField.getText(); // email address of person
			String password = passwordField.getText(); // password of person
			int month = Integer.parseInt((String)monthDropdown.getSelectedItem()); // month of DOB
			int day = Integer.parseInt((String)dayDropdown.getSelectedItem()); // day of DOB
			int year = Integer.parseInt((String)yearDropdown.getSelectedItem()); // year of DOB
			String sex = sexField.getText();
			String registrationCode = registrationCodeField.getText();
			String medicalHistory = "\n" + medicalHistoryField.getText();

			String dob = "" + month + "/" + day + "/" + year;
			String info;


			/////////////////////////////////////////////////////////////////
			// Validation to check wrong input
			/////////////////////////////////////////////////////////////////

			if (typeDropdown.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(frame, "Please select the type of user");

			// Check when an invalid date of birth is informed
			else if (year>2015 || ((month == 2 || month == 4 || month == 6 || month == 9 || month == 11) && day == 31))
				JOptionPane.showMessageDialog(frame, "Please select a valid Date of Birth.");

			// Check when the registration code is not informed for required users
			else if (typeDropdown.getSelectedIndex() >= 2 && registrationCode.equals(""))
				JOptionPane.showMessageDialog(frame, "Please fill all the required fields");

			/////////////////////////////////////////////////////////////////
			// Information will not be displayed back to the user but will be put into the database
       		// START TEST
       		/////////////////////////////////////////////////////////////////

			// Check when the user is a patient and display his information - Test Purpose
			else if (typeDropdown.getSelectedIndex() == 1){
				info = "First Name: " + firstName + "\nLast Name: " + lastName + "\nDate of Birth: " + dob
				+ "\nSex: " + sex + "\nEmail: " + emailAddress + "\nPassword: " + password
				+ "\nType: " + typeDropdown.getSelectedItem() + "\nMedical History: " + medicalHistory;
				JOptionPane.showMessageDialog(frame, info);
			}

			// Check when the user is not a patient and display his information (including registration code) - Test Purpose
			else if (typeDropdown.getSelectedIndex() >= 2 && !registrationCode.equals("")){
				info = "First Name: " + firstName + "\nLast Name: " + lastName + "\nDate of Birth: " + dob
				+ "\nSex: " + sex + "\nEmail: " + emailAddress + "\nPassword: " + password
				+ "\nType: " + typeDropdown.getSelectedItem() + "\nCredential ID: " + registrationCode;
				JOptionPane.showMessageDialog(frame, info);
			}
       	// END TEST
        }
    });

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quit program on close
    frame.setSize(300,480); // set size of window
    frame.setVisible(true); // show the window
  }

}
