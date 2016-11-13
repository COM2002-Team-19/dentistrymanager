package dentistrymanager;

@SuppressWarnings("serial")
public class DuplicateKeyException extends Exception {
	
	private String table;
	private String record;
	
	public DuplicateKeyException(String table, String record) {
		this.table = table;
		this.record = record;
	}
	
	public DuplicateKeyException(String table) {
		this(table, "");
	}
	
	public String getTable() {
		return table;
	}
	
	public String getRecord() {
		return record;
	}
}
