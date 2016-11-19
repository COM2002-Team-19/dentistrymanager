package dentistrymanager;

@SuppressWarnings("serial")
public class DeleteForeignKeyException extends Exception {
	
	// Instance variables
	private String table;
	private String record;
	
	// Constructors
	public DeleteForeignKeyException(String table, String record) {
		this.table = table;
		this.record = record;
	}
	public DeleteForeignKeyException(String table) {
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