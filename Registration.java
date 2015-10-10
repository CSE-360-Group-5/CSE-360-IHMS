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

    JLabel firstNameLabel = new JLabel("First name: "); // first name label
    JLabel lastNameLabel = new JLabel("Last name: "); // last name label
    JLabel emailLabel = new JLabel("Email address: "); // email address label
    JLabel passwordLabel = new JLabel("Password: "); // password label
    JLabel dobLabel = new JLabel("DOB: "); // DOB label
    JLabel typeLabel = new JLabel("Type: "); // type label
    JLabel registrationCodeLabel = new JLabel(""); // registration code label

    /////////////////////////////////////////////////////////////////
    // Text fields
    /////////////////////////////////////////////////////////////////

    JTextField firstNameField = new JTextField(15); // first name text field
    JTextField lastNameField = new JTextField(15); // last name text field
    JTextField emailField = new JTextField(15); // email address field
    JTextField passwordField = new JPasswordField(15); // password field
    JTextField registrationCodeField = new JTextField(15); // registration code field

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

    String[] type = {"Patient", "Doctor", "Nurse", "Healthcare Provider", "Lab Staff", "Pharmacist"}; // types that person could be
    JComboBox typeDropdown = new JComboBox<String>(type); // user type dropdown

    /////////////////////////////////////////////////////////////////
    // Buttons
    /////////////////////////////////////////////////////////////////

    JButton submitButton = new JButton("Submit"); // Submit button

    /////////////////////////////////////////////////////////////////
    // Frames and panels
    /////////////////////////////////////////////////////////////////

    JFrame frame = new JFrame("Registraton");

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

    JPanel typePanel = new JPanel(); // User type panel
    typePanel.add(typeLabel);
    typePanel.add(typeDropdown);

    JPanel submitPanel = new JPanel(); // Submit button panel
    submitPanel.add(submitButton);

    // Add panels to container
    Container content = frame.getContentPane();
    content.setLayout(new GridLayout(7,1));
    content.add(firstNamePanel);
    content.add(lastNamePanel);
    content.add(emailPanel);
    content.add(passwordPanel);
    content.add(dobPanel);
    content.add(typePanel);
    content.add(submitPanel);

    // Submit button listener
    submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
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

          // Information will not be displayed back to the user but will be put into the database
          // START TEST
          String dob = "" + month + "/" + day + "/" + year;
          String info = "First Name: " + firstName + "\nLast Name: " + lastName + "\nDate of Birth: " + dob + "\nEmail: " + emailAddress + "\nPassword: " + password + "\nType: " + typeDropdown.getSelectedItem();
		      JOptionPane.showMessageDialog(frame, info);
          // END TEST
        }
    });

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quit program on close
    frame.setSize(500,250); // set size of window
    frame.setVisible(true); // show the window
  }
}
