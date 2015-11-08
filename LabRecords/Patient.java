//////////////////////////////////////////////////////////////////
// Name: Patient.java
// Authors: Rian Martins
// Description: The class that defines the Patient Object
// Date: 10/23/2015
/////////////////////////////////////////////////////////////////

public class Patient
{
	private String firstName;
	private String lastName;
	private String dob;
	private String email;
	private String password;
	private String sex;

	private String maritalStatus;
	private String education;
	private String race;

	//Default constructor to initialize main member variables
	public Patient()
	{
		firstName = "???";
		lastName = "???";
		dob = "??/??/????";
	}

	//Constructor to initialize main member variables to parsed arguments
	public Patient(String fName, String lName, String date)
	{
		firstName = fName;
		lastName = lName;
		dob = date;
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

	//Getter method to access the email address of the patient
	public String getEmail()
	{
		return email;
	}

	//Getter method to access the password of the patient
	public String getPassword()
	{
		return password;
	}

	//Getter method to access the sex of the patient
	public String getSex()
	{
		return sex;
	}

	//Getter method to access the marital status of the patient
	public String getMaritalStatus()
	{
		return maritalStatus;
	}

	//Getter method to access the education of the patient
	public String getEducation()
	{
    		return education;
	}

	//Getter method to access the race of the patient
	public String getRace()
	{
		return race;
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

	//Mutator method to set the email of the patient
	public void setEmail(String _email)
	{
		email = _email;
	}

	//Mutator method to set the password of the patient
	public void setPassword(String _password)
	{
		password = _password;
	}

	//Mutator method to set the sex of the patient
	public void setSex(String _sex)
	{
		sex = _sex;
	}

	//Mutator method to set the data for generation of statistical report
	public void setSocialData(String _maritalStatus, String _education, String _race)
	{
		maritalStatus = _maritalStatus;
		education = _education;
		race = _race;
	}

	//toString() method returns a string containg First name, Last name and date of the record.
	public String toString()
	{
		String result = "\nFirst Name: " + firstName + "; \nLast Name: " + lastName + "; \nDate of birth: " + dob;
		return result;
	}
	
  }
