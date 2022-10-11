public class FlashCard {
	private String term;
	private String definition;

  	public FlashCard(String t, String d){
		term = t;
		definition = d;
  	}

	public void setTerm(String term){
		this.term = term;
	}	

	public String getTerm(){
		return term;
	}

	public void setDefinition(String definition){
		this.definition = definition;
	}

	public String getDefinition(){
		return definition;
	}
}