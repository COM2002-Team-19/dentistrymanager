package dentistrymanager;

import java.sql.*;
import java.lang.Number;

public class DBUtilities {
	
	public static final String BLANKS = "";
	
	public static String nullToBlanks(String value) {
		try {
			return value == null ? BLANKS : value;
		} catch(NullPointerException e) {
			return BLANKS;
		}
	}
	
	public static int nullToZero(String value) {
		try {
			return Integer.parseInt(value);
		} catch(NumberFormatException e) {
			return 0;
		}
	}
	//takes date makes it into accepted Long format
	public static Long dateToLong (Date d) {
		String dStr = d.toString();
		dStr = dStr.replace("-", "");
		Long dLong = new Long(dStr);
		return dLong;
	}	
	
}