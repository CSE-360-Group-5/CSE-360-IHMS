//////////////////////////////////////////////////////////////////
// Name: Records.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: The class that defines the Patient Object used in LabRecords.java
// Date: 10/30/2015
/////////////////////////////////////////////////////////////////

public class Patient
 {
   private String firstName;
   private String lastName;
   private String dob;

   //Constructor to initialize all member variables
   public Patient()
    {
      firstName = "???";
      lastName = "???";
      dob = "??/??/????";
    }

   //Constructor to initialize all member variables
   public Patient(String fName, String lName, String date)
    {
      firstName = fName;
      lastName = lName;
      dob = date;
    }

   //Accessor method to access the first name of the patient
   public String getFirstName()
    {
    	return firstName;
    }

   //Accessor method to access the last name of the patient
   public String getLastName()
    {
    	return lastName;
    }

   //Accessor method to access the date of birth of the patient
   public String getDOB()
    {
    	return dob;
    }

   //Mutator method to access the first name of the patient
   public void setFirstName(String _firstName)
   {
	firstName = _firstName;
   }

   //Mutator method to access the last name of the patient
   public void setLastName(String _lastName)
   {
	lastName = _lastName;
   }

   //Mutator method to access the date of birth of the patient
   public void setDOB(String _dob)
   {
	dob = _dob;
   }

   //toString() method returns a string containg First name, Last name and date of the record.
   public String toString()
    {
      String result = "\nFirst Name: " + firstName + "; \nLast Name: " + lastName
                    + "; \nDate of birth: " + dob;
      return result;
     }
  }