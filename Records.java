//////////////////////////////////////////////////////////////////
// Name: Records.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: The class that defines the Lab Record Object used in LabRecords.java
// Date: 10/23/2015
/////////////////////////////////////////////////////////////////

public class Records
 {
   private String firstName;
   private String lastName;
   private String dob;
   private String date;
   private String labRecord;

   //Constructor to initialize all member variables
   public Records()
    {
      firstName = "???";
      lastName = "???";
      dob = "?/?/?";
      date = "?/?/?";
      labRecord = "Empty lab record";
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

    public String getDate()
    {
	      return date;
    }
    public String getRecord()
  	{
		  return labRecord;
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


   //Mutator method to access its date
   public void setDate(String _date)
   {
	   date = _date;
   }

   //Mutator method to access the actual lab record
   public void setRecord(String _labRecord)
   {
	   labRecord = _labRecord;
   }

   //toString() method returns a string containg First name, Last name and date of the record.
   public String toString()
    {
      String result = "\nFirst Name: " + firstName + "; \nLast Name: " + lastName
                    + "; \nDate of the record: " + date;
      return result;
     }
  }
