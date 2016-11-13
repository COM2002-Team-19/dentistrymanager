package dentistrymanager;

@SuppressWarnings("serial")
public class DeleteForeignKeyException extends Exception {
	
	private String table;
	private String record;
	
	public DeleteForeignKeyException(String table, String record) {
		this.table = table;
		this.record = record;
	}
	
	public DeleteForeignKeyException(String table) {
		this(table, "");
	}
	
	public String getTable() {
		return table;
	}
	
	public String getRecord() {
		return record;
	}

}
