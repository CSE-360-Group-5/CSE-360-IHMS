//////////////////////////////////////////////////////////////////
// Name: statsReport.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the generation of the statistical report
// Date: 10/9/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class statsReport {
  public static void main(String[] args) {
    // GUI elements
    JButton submitButton = new JButton("Submit");

    // Arrays for information
    // Will be filled by database during integration component of project

    // Sex array (Male, Female)
    String[] sex = {"Male", "Female", "Male", "Male", "Male", "Female", "Female", "Male", "Female", "Male", "Male", "Female", "Male", "Male", "Male", "Female", "Female", "Male", "Female", "Male"}
    // Marital status array (Single, Married)
    String[] maritalStatus = {"Single", "Married", "Single", "Married", "Married", "Single", "Married", "Single", "Single", "Married", "Single", "Single", "Married", "Married", "Married", "Single", "Single", "Married", "Single", "Married"};
    // Education array (No Degree, High School or Equivalent, Some College, Bachelors, Masters, Doctorate)
    String[] education = {"No Degree", "Some College", "High School or Equivalent", "Bachelors", "Some College", "High School or Equivalent", "Bachelors", "Masters", "No Degree", "Doctorate", "Masters", "High School or Equivalent", "Bachelors", "No Degree", "Doctorate", "Masters", "Bachelors", "Doctorate", "High School or Equivalent", "Bachelors"};
    // Race array (Asian, Latino, Black/African-American, Pacific Islander, Caucasian, Other
    String[] race = {"Asian", "Latino", "Other", "Black/African-American", "Black/African-American", "Latino", "Other", "Asian", "Asian", "Caucasian", "Caucasian", "Latino", "Pacific Islander", "Caucasian", "Asian", "Pacific Islander", "Caucasian", "Caucasian", "Caucasian", "Other"};
    // Patient (Pediatric, Vascular, Neurologic, Gynocology, Orthopedic, Other)
    String [] patient type = {"Vascular", "Gynocology", "Pediatric", "Orthopedic", "Vascular", "Pediatric", "Gynocology", "Orthopedic", "Pediatric", "Orthopedic", "Vascular", "Neurologic", "Neurologic", "Pediatric", "Orthopedic", "Neurologic", "Pediatric", "Other", "Other", "Neurologic"};

    // Functional logic
    submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            
        }
      });
  }
}
