package dentistrymanager;

@SuppressWarnings("serial")
public class DuplicateKeyException extends Exception {
	
	// Instance variables
	private String table;
	private String record;
	
	// Constructor
	public DuplicateKeyException(String table, String record) {
		this.table = table;
		this.record = record;
	}
	public DuplicateKeyException(String table) {
		this(table, "");
	}
	
	// Accessors
	public String getTable() {
		return table;
	}
	public String getRecord() {
		return record;
	}
}