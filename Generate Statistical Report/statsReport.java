//////////////////////////////////////////////////////////////////
// Name: statsReport.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the generation of the statistical report
// Date: 10/9/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class statsReport {
  public static void main(String[] args) {
    // GUI elements and construction
    JButton submitButton = new JButton("Generate");

    JFrame frame = new JFrame("Statistical Reports");

    JPanel submitPanel = new JPanel();
    submitPanel.add(submitButton);

    Container container = frame.getContentPane();
    container.setLayout(new GridLayout(1,1));

    container.add(submitPanel);

    // Arrays for information
    // Will be filled by database during integration component of project

    // Sex array (Male, Female)
    String[] sex = {"Male", "Female", "Male", "Male", "Male", "Female", "Female", "Male", "Female", "Male", "Male", "Female", "Male", "Male", "Male", "Female", "Female", "Male", "Female", "Male"};
    // Marital status array (Single, Married)
    String[] maritalStatus = {"Single", "Married", "Single", "Married", "Married", "Single", "Married", "Single", "Single", "Married", "Single", "Single", "Married", "Married", "Married", "Single", "Single", "Married", "Single", "Married"};
    // Education array (No Degree, High School or Equivalent, Some College, Bachelors, Masters, Doctorate)
    String[] education = {"No Degree", "Some College", "High School or Equivalent", "Bachelors", "Some College", "High School or Equivalent", "Bachelors", "Masters", "No Degree", "Doctorate", "Masters", "High School or Equivalent", "Bachelors", "No Degree", "Doctorate", "Masters", "Bachelors", "Doctorate", "High School or Equivalent", "Bachelors"};
    // Race array (Asian, Latino, Black/African-American, Pacific Islander, Caucasian, Other
    String[] race = {"Asian", "Latino", "Other", "Black/African-American", "Black/African-American", "Latino", "Other", "Asian", "Asian", "Caucasian", "Caucasian", "Latino", "Pacific Islander", "Caucasian", "Asian", "Pacific Islander", "Caucasian", "Caucasian", "Caucasian", "Other"};
    // Patient (Pediatric, Vascular, Neurologic, Gynocology, Orthopedic, Other)
    String [] patientType = {"Vascular", "Gynocology", "Pediatric", "Orthopedic", "Vascular", "Pediatric", "Gynocology", "Orthopedic", "Pediatric", "Orthopedic", "Vascular", "Neurologic", "Neurologic", "Pediatric", "Orthopedic", "Neurologic", "Pediatric", "Other", "Other", "Neurologic"};

    // Functional logic
    submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          // initialize all count variables to zero
          int numOfMales = 0;
          int numOfFemales = 0;
          int numOfSing = 0;
          int numOfMar = 0;
          int numOfND = 0;
          int numOfHS = 0;
          int numOfSC = 0;
          int numOfBach = 0;
          int numOfMast = 0;
          int numOfDoc = 0;
          int numOfAsian = 0;
          int numOfLat = 0;
          int numOfBAA = 0;
          int numOfPacIs = 0;
          int numOfCauc = 0;
          int numOfOtherRace = 0;
          int numOfVasc = 0;
          int numOfGyno = 0;
          int numOfPed = 0;
          int numOfOrtho = 0;
          int numOfNeuro = 0;
          int numOfOtherType = 0;

          // gender counts
          for (int i = 0; i < sex.length; i++) {
            if (sex[i].equalsIgnoreCase("Male"))
              numOfMales++;
            else
              numOfFemales++;
          }

          // marital status counts
          for (int i = 0; i < maritalStatus.length; i++) {
            if (maritalStatus[i].equalsIgnoreCase("Single"))
              numOfSing++;
            else
              numOfMar++;
          }

          // education counts
          for (int i = 0; i < education.length; i++) {
            if (education[i].equalsIgnoreCase("No Degree"))
              numOfND++;
            else if (education[i].equalsIgnoreCase("High School or Equivalent"))
              numOfHS++;
            else if (education[i].equalsIgnoreCase("Some College"))
              numOfSC++;
            else if (education[i].equalsIgnoreCase("Bachelors"))
              numOfBach++;
            else if (education[i].equalsIgnoreCase("Masters"))
              numOfMast++;
            else
              numOfDoc++;
          }

          // race counts
          for(int i = 0; i < race.length; i++) {
            if (race[i].equalsIgnoreCase("Asian"))
              numOfAsian++;
            else if (race[i].equalsIgnoreCase("Latino"))
              numOfLat++;
            else if (race[i].equalsIgnoreCase("Black/African-American"))
              numOfBAA++;
            else if (race[i].equalsIgnoreCase("Caucasian"))
              numOfCauc++;
            else if (race[i].equalsIgnoreCase("Pacific Islander"))
              numOfPacIs++;
            else
              numOfOtherRace++;
          }

          // patient type counts
          for(int i = 0; i < patientType.length; i++) {
            if (patientType[i].equalsIgnoreCase("Vascular"))
              numOfVasc++;
            else if (patientType[i].equalsIgnoreCase("Pediatric"))
              numOfPed++;
            else if (patientType[i].equalsIgnoreCase("Neurologic"))
              numOfNeuro++;
            else if (patientType[i].equalsIgnoreCase("Gynocology"))
              numOfGyno++;
            else if (patientType[i].equalsIgnoreCase("Orthopedic"))
              numOfOrtho++;
            else
              numOfOtherType++;
          }

          // generate the report
          String statsReport =
          "Total number of patients in time span: " + sex.length + "\n" +
          "-----------------------------------------\n" +
          "Gender statistics: \n" +
          "Number of males: " + numOfMales + " (" + numOfMales / (double)sex.length * 100 + "%)\n" +
          "Number of females: " + numOfFemales + " (" + numOfFemales / (double)sex.length * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Marital status statistics: \n" +
          "Number of singles: " + numOfSing + " (" + numOfSing / (double)maritalStatus.length * 100 + "%)\n" +
          "Number of married: " + numOfMar + " (" + numOfMar / (double)maritalStatus.length * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Education statistics: \n" +
          "Number of no degrees: " + numOfND + " (" + numOfND / (double)education.length * 100 + "%)\n" +
          "Number of high school diplomas: " + numOfHS + " (" + numOfHS / (double)education.length * 100 + "%)\n" +
          "Number of some college experience: " + numOfSC + " (" + numOfSC / (double)education.length * 100 + "%)\n" +
          "Number of Bachelors: " + numOfBach + " (" + numOfBach / (double)education.length * 100 + "%)\n" +
          "Number of Masters: " + numOfMast + " (" + numOfMast / (double)education.length * 100 + "%)\n" +
          "Number of Doctorate: " + numOfDoc + " (" + numOfDoc / (double)education.length * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Race statistics: \n" +
          "Number of Caucasians: " + numOfCauc + " (" + numOfCauc / (double)race.length * 100 + "%)\n" +
          "Number of Latinos: " + numOfLat + " (" + numOfLat / (double)race.length * 100 + "%)\n" +
          "Number of Asians: " + numOfAsian + " (" + numOfAsian / (double)race.length * 100 + "%)\n" +
          "Number of Black/African-Americans: " + numOfBAA + " (" + numOfBAA / (double)race.length * 100 + "%)\n" +
          "Number of Pacific Islanders: " + numOfPacIs + " (" + numOfPacIs / (double)race.length * 100 + "%)\n" +
          "Number of Others: " + numOfOtherRace + " (" + numOfOtherRace / (double)race.length * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Patient type statistics: \n" +
          "Number of vascular patients: " + numOfVasc + " (" + numOfVasc / (double)patientType.length * 100 + "%)\n" +
          "Number of pediatric patients: " + numOfPed + " (" + numOfPed / (double)patientType.length * 100 + "%)\n" +
          "Number of neurologic patients: " + numOfNeuro + " (" + numOfNeuro / (double)patientType.length * 100 + "%)\n" +
          "Number of gynocology patients: " + numOfGyno + " (" + numOfGyno / (double)patientType.length * 100 + "%)\n" +
          "Number of orthopedic patients: " + numOfOrtho + " (" + numOfOrtho / (double)patientType.length * 100 + "%)\n" +
          "Number of other patients: " + numOfOtherType + " (" + numOfOtherType / (double)patientType.length * 100 + "%)\n" +
          "-----------------------------------------";

          JOptionPane.showMessageDialog(frame, statsReport);
        }
      });

      // set frame size and conditions
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quit program on close
      frame.setSize(150,150); // set size of window
      frame.setVisible(true); // show the window
  }
}
