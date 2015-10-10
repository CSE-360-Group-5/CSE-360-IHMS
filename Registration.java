import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Registration {
  /////////////////////////////////////////////////////////////////
  // Main program
  /////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    /////////////////////////////////////////////////////////////////
    // GUI variables
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
    JButton submitButton = new JButton("Submit"); // submit button
    /////////////////////////////////////////////////////////////////
    JFrame frame = new JFrame("Registraton");

    JPanel firstNamePanel = new JPanel();
    firstNamePanel.add(firstNameLabel);
    firstNamePanel.add(firstNameField);

    JPanel lastNamePanel = new JPanel();
    lastNamePanel.add(lastNameLabel);
    lastNamePanel.add(lastNameField);

    JPanel emailPanel = new JPanel();
    emailPanel.add(emailLabel);
    emailPanel.add(emailField);

    JPanel passwordPanel = new JPanel();
    passwordPanel.add(passwordLabel);
    passwordPanel.add(passwordField);

    JPanel dobPanel = new JPanel();
    dobPanel.add(dobLabel);
    dobPanel.add(monthDropdown);
    dobPanel.add(dayDropdown);
    dobPanel.add(yearDropdown);

    JPanel typePanel = new JPanel();
    typePanel.add(typeLabel);
    typePanel.add(typeDropdown);

    // JPanel registrationCodePanel = new JPanel();

    JPanel submitPanel = new JPanel();
    submitPanel.add(submitButton);

    Container content = frame.getContentPane();
    content.setLayout(new GridLayout(7,1));
    content.add(firstNamePanel);
    content.add(lastNamePanel);
    content.add(emailPanel);
    content.add(passwordPanel);
    content.add(dobPanel);
    content.add(typePanel);
    content.add(submitPanel);

    submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          /////////////////////////////////////////////////////////////////
          // data variables for the user
          /////////////////////////////////////////////////////////////////
          String firstName = ""; // first name of person
          String lastName = ""; // last name of person
          String emailAddress = ""; // email address of person
          String password = ""; // password of person
          int month = 0; // month of DOB
          int day = 0; // day of DOB
          int year = 0; // year of DOB
          int registrationCode = 0; // registration code if person is an internal user of the program (everything not patient)

          firstName = firstNameField.getText();
          lastName = lastNameField.getText();
          emailAddress = emailField.getText();
          password = passwordField.getText();
          month = Integer.parseInt((String)monthDropdown.getSelectedItem());
          day = Integer.parseInt((String)dayDropdown.getSelectedItem());
          year = Integer.parseInt((String)yearDropdown.getSelectedItem());

          String dob = "" + month + "/" + day + "/" + year;
          String info = "First Name: " + firstName + "\nLast Name: " + lastName + "\nDate of Birth: " + dob + "\nEmail: " + emailAddress + "\nPassword: " + password + "\nType: " + typeDropdown.getSelectedItem();
		      // default title and icon
		      JOptionPane.showMessageDialog(frame, info);
        }
    });

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500,250);
    frame.setVisible(true);
  }
}
