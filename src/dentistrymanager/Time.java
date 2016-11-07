package dentistrymanager;

public class Time {
	
	//Variables
	private int hour;
	private int minute;
	
	//Constructor
	/*
	@param h the hour
	@param m the minutes
	*/
	public Time(int h, int m) {
		if (Math.abs(h)<24)
			this.hour = Math.abs(h);
		if (Math.abs(m)<60)
			this.minute = Math.abs(m);
	}
	
	//Accessors
	public int getHour(){ return hour;}
	public int getMin(){ return minute;}
	
	//Mutators
	public void setHour(int h){ this.hour = h;}
	public void setMin(int m){ this.minute = m;}
	
	//Prints in "hh:mm" format
	public String toString(){
		String s = "";
		if (getHour()<10)
			s+= "0"+getHour();
		else
			s+= getHour();
		s+= ":";
		if (getMin()<10)
			s+= "0"+getMin();
		else
			s+= getMin();
		
		return s;
	}
}
