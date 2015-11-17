
public class Appointment {
	String doctor, month, year, date, time;
	int  row, column,patient,id;
	static boolean fin;
	public Appointment(String d, String m, String y, String da, String t, int r, int col, int p,int i){
		patient=p;
		doctor=d;
		month=m;
		year=y;
		date=da;public class Appointment {
	String doctor, month, year, date, time;
	int  row, column,patient,id;
	String fin, status;
	public Appointment(String d, String m, String y, String da, String t, int r, int col, int p,int i){
		patient=p;
		doctor=d;
		month=m;
		year=y;
		date=da;
		time=t;
		row = r;
		id=i;
		column = col;
		fin="false";
		status = "pending";
		
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
	public String getRow(){
		String r = Integer.toString(row);
		return r;
	}
	public String getColumn(){
		String col = Integer.toString(column);
		return col;
	}
	public String getStatus(){
		return status;
	}
	public String getFin(){
		return fin;
	}
	public void setFin(String f){
		fin=f;
	}
	
	public void setStatus(String st){
		status=st;
	}
	
	public String toStringPatient()
	{
		String result = "* " + month + "/" + date + "/" + year + " at " + time + " with Dr." + doctor + "\n";
		return result;
	}
	public String toStringDoctor()
	{
		String result = "* " + month + "/" + date + "/" + year + " at " + time + "\n";
		return result;
	}
}
		time=t;
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
	public String getRow(){
		String r = Integer.toString(row);
		return r;
	}
	public String getColumn(){
		String col = Integer.toString(column);
		return col;
	}
	public boolean getFin(){
		return fin;
	}
	public void setFin(boolean f){
		fin=f;
	}
}
