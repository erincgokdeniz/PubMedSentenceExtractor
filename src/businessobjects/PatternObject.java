package businessobjects;

/**
 * @author gokdeniz
 * This class is the base class for each pattern (i.e. brain region, mood)
 * */
public class PatternObject {

	private String type;
	private String patternText;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPatternText() {
		return patternText;
	}
	public void setPatternText(String patternText) {
		this.patternText = patternText;
	}
	
	
	
}
