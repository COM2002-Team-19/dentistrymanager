package dentistrymanager;

public class Patient {
	
	// Instance variables
	private Title title;
	private String forename;
	private String surname;
	private long dob; // date of birth in format YYYYMMDD
	private long phoneNo;
	private Address address;
	
	// Default values
	private static final Title DEFAULT_TITLE = null;
	private static final String DEFAULT_F_NAME = "blank";
	private static final String DEFAULT_S_NAME = "blank";
	private static final long DEFAULT_DOB = 00000000;
	private static final long DEFAULT_PHONE = 00000000000;
	private static final Address DEFAULT_ADDRESS = null;
	
	// Constructor
	public Patient(Title t, String f, String s, long d, long p, Address a){
		title = t;
		forename = f.toUpperCase().substring(0, 1) + f.substring(1).toLowerCase(); // saves in form Xx...xx
		surname = s.toUpperCase().substring(0, 1) + s.substring(1).toLowerCase();
		dob = d;
		phoneNo = p;
		address = a;
	}
	
	// Default constructor, chained
	public Patient() {
		this(DEFAULT_TITLE, DEFAULT_F_NAME, DEFAULT_S_NAME, DEFAULT_DOB, DEFAULT_PHONE, DEFAULT_ADDRESS);
	}

	// Accessors
	public Title getTitle() { return title;	}
	public String getForename() { return forename;}
	public String getSurname() { return surname;}
	public long getDob() { return dob;}
	public long getPhoneNo() { return phoneNo;}
	public Address getAddress() { return address;}

	// Mutators
	public void setTitle(Title title) {	this.title = title;}
	public void setForename(String forename) { this.forename = forename;}
	public void setSurname(String surname) { this.surname = surname;}
	public void setDob(long dob) { this.dob = dob;}
	public void setPhoneNo(long phoneNo) { this.phoneNo = phoneNo;}
	public void setAddress(Address address) { this.address = address;}
	
	public String toString() {
		String s = "";
		if (title != null)
			s += title + " ";
		s += this.forename + " " + this.surname + "\nBorn: " + this.dob + "\nContact number: " + this.phoneNo;
		if (address != null)
			s += "\n" + this.address;
		return s;
	}
}
