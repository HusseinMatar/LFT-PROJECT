public class NumberTok extends Token {
	// ... completare ...
	public int lexeme;

  	public NumberTok(int n){
    	super(Tag.NUM);
    	this.lexeme = n;
  	}

  	public String toString(){
    	return "<" + tag + ", " + Integer.toString(lexeme) + ">";
  	}
	
}
