package dentistrymanager;

public enum Title {
	MR,
	MRS,
	MS,
	MISS,
	MASTER,
	DR;
	
	public static Title called(String s) {
		if  (  s != null )
			switch(s.replace(".","").toUpperCase()) {
				case "MR" :
				case "MRS"  :
				case "MS" :
				case "MISS" : 
				case "MASTER" :
				case "DR" :  
					return Title.valueOf(s.toUpperCase());
				default :
					return null;
			}
		return null;
	}
	
	public String toString(){
		String s = name().charAt(0) + name().substring(1).toLowerCase();
		switch(this) {
			case DR :
			case MR :
			case MRS :
			case MS :
				s += ".";
			default :
				s = "";
		}
		return s;
	}
}
