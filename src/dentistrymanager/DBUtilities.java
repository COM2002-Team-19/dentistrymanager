package dentistrymanager;

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
}