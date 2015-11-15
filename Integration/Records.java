//////////////////////////////////////////////////////////////////
// Name: Records.java
// Authors: Rian Martins
// Description: The class that defines the Lab Record Object used in LabRecords.java
// Date: 10/23/2015
/////////////////////////////////////////////////////////////////

public class Records
{
	int patientID;
	private String firstName;
	private String lastName;
	private String dob;
	private String date;
	private String labRecord;

	//Default constructor to initialize all member variables
	public Records()
	{
		patientID = 0;
		firstName = "???";
		lastName = "???";
		dob = "??/??/????";
		date = "??/??/????           ";
		labRecord = "Empty lab record";
	}

	//Getter method to access the id of the patient
  	public int getPatientID()
	{
  		return patientID;
	}
	
	//Getter method to access the first name of the patient
  	public String getFirstName()
	{
  		return firstName;
	}

	//Getter method to access the last name of the patient
	public String getLastName()
	{
		return lastName;
	}

	//Getter method to access the date of birth of the patient
	public String getDOB()
	{
		return dob;
	}

	//Getter method to access the date of the lab record upload
	public String getDate()
	{
		return date;
	}

	//Getter method to access the lab record
	public String getRecord()
	{
		return labRecord;
	}

	//Mutator method to set the id of the patient
	public void setPatientId(int id)
	{
		patientID = id;
	}
	
	//Mutator method to set the first name of the patient
	public void setFirstName(String _firstName)
	{
		firstName = _firstName;
	}

	//Mutator method to set the last name of the patient
	public void setLastName(String _lastName)
	{
		lastName = _lastName;
	}

	//Mutator method to set the date of birth of the patient
	public void setDOB(String _dob)
	{
		dob = _dob;
	}

	//Mutator method to set its date
	public void setDate(String _date)
	{
		date = _date;
	}

	//Mutator method to set the actual lab record
	public void setRecord(String _labRecord)
	{
		labRecord = _labRecord;
	}

	//toString() method returns a string containing First name, Last name and date of the record.
	public String toString()
	{
		String result = "\nFirst Name: " + firstName + "; \nLast Name: " + lastName + "; \nDate of the record: " + date;
		return result;
	}

}
