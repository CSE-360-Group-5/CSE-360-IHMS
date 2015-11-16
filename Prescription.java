
public class Prescription {
	String drug,patient,doctor,directions;
	public String report;
	int amount,date, month, year, patID, patDOB;
	public Prescription(String d, String p, String doc, String di,int am, int da, int m, int y, String re, int id, int dob){
		drug=d;
		patient=p;
		doctor=doc;
		directions=di;
		amount=am;
		date=da;
		month=m;
		year=y;
		report = re;
		patID = id;
		patDOB = dob;
	}
	String getDrug(){
		return drug;
	}
	String getPat(){
		return patient;
	}
	String getDoc(){
		return doctor;
	}
	String getDir(){
		return directions;
	}
	int getAmount(){
		return amount;
	}
	int getDate(){
		return date;
	}
	int getMonth(){
		return month;
	}
	int getYear(){
		return year;
	}
	public String getReport(){
		return report;
	}
	public void setReport(String edit){
		report = edit;
	}
	String getID(){
		String patID2 = Integer.toString(patID);
		return patID2;
	}
	String getDOB(){
		String patDOB2 = Integer.toString(patDOB);
		return patDOB2;
	}
}
