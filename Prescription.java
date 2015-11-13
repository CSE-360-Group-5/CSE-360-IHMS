

public class Prescription
{
	private String prescriptionID;
	private String patientID;
	private String prescriptionInfo;
	private String date;
	private boolean isMed; //bool: 1 if meds, 0 if lab
	private boolean isFilled; //bool: 1 if filled, 0 if not filled

	public Prescription()
	{
		prescriptionID = "???";
		patientID = "???";
		prescriptionInfo = "Empty prescription";
		date = "??/??/????";
	}

	public String getPrescriptionID()
	{
		return prescriptionID;
	}

	public String getPatientID()
	{
		return patientID;
	}

	public String getPrescriptionInfo()
	{
		return prescriptionInfo;
	}

	public String getDate()
	{
		return date;
	}

	public boolean isMedication()
	{
		return isMed;
	}

	public boolean isFilled()
	{
		return isFilled;
	}

	public void setPrescriptionID(String id)
	{
		prescriptionID = id;
	}

	public void setPatientID(String id)
	{
		patientID = id;
	}

	public void setPrescriptionInfo(String info)
	{
		prescriptionInfo = info;
	}

	public void setDate(String d)
	{
		date = d;
	}

	public void setIsMedication(boolean med)
	{
		isMed = med;
	}

	public void setIsFilled(boolean filled)
	{
		isFilled = filled;
	}
}
