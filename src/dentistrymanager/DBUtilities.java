package dentistrymanager;

public class DBUtilities {
	
	public static final String NULL = "null";
	public static final String BLANKS = "";
	
	public static String nullToBlanks(String value) {
		return value.equals(NULL) ? BLANKS : value;
	}
	
	public static int nullToZero(String value) {
		try {
			return value.equals(NULL) ? 0 : Integer.parseInt(value);
		} catch(NullPointerException e) {
			return 0;
		}
	}
}