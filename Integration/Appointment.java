//////////////////////////////////////////////////////////////////
// Name: Appointment.java
// Authors: Gareth Whitehead, Brandon Namie, Joshua Lidell
// Description: This class defines the Appointment object
// Date: 10/15/2015
/////////////////////////////////////////////////////////////////

public class Appointment {
	static String doctor, month, year, date, time, type;
	static int  row, column,patient,id;
	static boolean fin;
	public Appointment(String d, String m, String y, String da, String t, String docType, int r, int col, int p,int i){
		patient=p;
		doctor=d;
		month=m;
		year=y;
		date=da;
		time=t;
		type = docType;
		row = r;
		id=i;
		column = col;
		fin=false;
		
	}
	public int getId(){
		return id;
	}
	public int getPatient(){
		return patient;
	}
	public String getDoctor(){
		return doctor;
	}
	public String getMonth(){
		//String m = Integer.toString(month);
		return month;
	}
	public String getYear(){
		//String ye = Integer.toString(year);
		return year;
	}
	public String getDate(){
		//String dat = Integer.toString(date);
		return date;
	}
	public String getTime(){
		//String ti = Integer.toString(time);
		return time;
	}
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}
	public boolean getFin(){
		return fin;
	}
	public void setFin(boolean f){
		fin=f;
	}
	public String toStringPatient()
	{
		String result = " * " + month + "/" + date + "/" + year + " - at " + time + " with Dr. " + doctor + "\n";
		return result;
	}
	public String toStringDoctor()
	{
		String result = " * " + month + "/" + date + "/" + year + " - at " + time + "\n";
		return result;
	}
}
