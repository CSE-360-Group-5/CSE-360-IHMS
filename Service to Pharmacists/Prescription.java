//////////////////////////////////////////////////////////////////
// Name: Prescription.java
// Authors: Nathan Chancellors
// Description: The class that defines the Prescription Object used in Pharmacist.java
// Date: 11/15/2015
/////////////////////////////////////////////////////////////////

public class Prescription {
	private int patientID;
	private String prescriptName;
	private int prescriptID;
	private String type;
	private String date;
	private String filled;

	// Default constructor to initialize all member variables
	public Prescription() {
		patientID = 0;
		prescriptName = "???";
		prescriptID = 0;
		type = "???";
		date = "??/??/????";
		filled = "No";
	}

	// Getter method to access the id of the patient
  public int getPatientID() {
  		return patientID;
	}

	// Getter method to access the name of the prescription
  public String getPrescriptName() {
  		return prescriptName;
	}

	// Getter method to access the ID of the prescription
	public int getPrescriptID() {
		return prescriptID;
	}

	// Getter method to access the type of prescription
	public String getType() {
		return type;
	}

	// Getter method to access the date of the prescription
	public String getDate() {
		return date;
	}

	// Getter method to whether or not the prescription has been filled
	public String getFilled() {
		return filled;
	}

	//Mutator method to set the id of the patient
	public void setPatientID(int id) {
		patientID = id;
	}

	//Mutator method to set the first name of the patient
	public void setPrescriptName(String _prescriptName) {
		prescriptName = _prescriptName;
	}

	//Mutator method to set the last name of the patient
	public void setPrescriptID(String _prescriptID) {
		prescriptID = _prescriptID;
	}

	//Mutator method to set the date of birth of the patient
	public void setType(String _type) {
		type = _type;
	}

	//Mutator method to set its date
	public void setDate(String _date) {
		date = _date;
	}

	//Mutator method to set the actual lab record
	public void setFilled(String _filled) {
		filled = _filled;
	}

	//toString() method returns a string containing the variables.
	public String toString() {
		String result = "\nPatient ID: " + patientID + "\nPrescription: " + prescriptName + "\nPrescription ID: " + prescriptID + "\nType: " + type + "\nDate of the record: " + date + "\nFilled: " + filled;
		return result;
	}

}
