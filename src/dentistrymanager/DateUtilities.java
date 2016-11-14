package dentistrymanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtilities {
	
	// Constants
	public static final int WEEK_LENGTH = 5;
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	
	// Methods
	public static String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());	
	}
	
	public static String startWeek(int week) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		return DATE_FORMAT.format(cal.getTime());
	}
	
	public static String endWeek(int week) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, week);
		cal.add(Calendar.DATE, WEEK_LENGTH);
		return DATE_FORMAT.format(cal.getTime());
	}
	
	public static void main(String[] args) {
		
		System.out.println(DateUtilities.startWeek(0));
		System.out.println(DateUtilities.endWeek(0));
	}

}
