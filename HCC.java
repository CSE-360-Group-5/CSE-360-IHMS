import java.util.*;

public class HCC
{
	private String patientID;
	private String hccID;
	private String HCC;
	private String date;
	
	public HCC()
	{

	}
	
	//Getters
	public String getPatientID()
	{
		return patientID;
	}
	
	public String gethccID()
	{
		return hccID;
	}
	
	public String getHCC()
	{
		return HCC;
	}
	
	public String getDate()
	{
		return date;
	}
	
	
	public void setPatientID(String id)
	{
		patientID = id;
	}
	
	public void sethccID(String id)
	{
		hccID = id;
	}
	
	public void setHCC(String c)
	{
		HCC = c;
	}
	
	public void setDate(String d)
	{
		date = d;
	}
}

