package dentistrymanager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Time;

public class DateTimeUtilities {
	
	// Constants
	public static final int WEEK_LENGTH = 5;
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final int YEAR_FROM_TODAY = 1;
	
	// Methods
	public static String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());	
	}
	
	public static String oneYearFromToday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, YEAR_FROM_TODAY);
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
	
	public static int thisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(cal.getTime());
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static Date stringToDate(String year, String month, String day) {
		return Date.valueOf(year + "-" + month + "-" + day);
	}
	
	public static Time stringToTime(String hours, String minutes) {
		return Time.valueOf(hours + ":" + minutes + ":" + "00");
	}
	
	public static Time stringToTime(String hourMinutes) {
		
		// Breaks down the time string
		String hours = hourMinutes.substring(0, 2);
		String minutes = hourMinutes.substring(hourMinutes.length() - 2, hourMinutes.length());
		return DateTimeUtilities.stringToTime(hours, minutes);
	}
	
	public static void main(String[] args) {
		
		//System.out.println(DateTimeUtilities.startWeek(0));
		//System.out.println(DateTimeUtilities.endWeek(0));
		System.out.println(stringToDate("2016", "11","20"));
		System.out.println(stringToTime("16", "21"));
		System.out.println(stringToTime("1621"));
	}

}
