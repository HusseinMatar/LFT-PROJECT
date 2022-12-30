import java.io.*;

public class Parser {

	private Lexer lex;
	private BufferedReader pbr;
	private Token look;

	public Parser(Lexer l, BufferedReader br) {
		lex = l;
		pbr = br;
		move();
	}

	void move() {
		look = lex.lexical_scan(pbr);
		System.out.println("token = " + look);
	}

	void error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
	}

	void match(int t) {
		if (look.tag == t) {
		if (look.tag != Tag.EOF) move();
		} else error("syntax error");
	}

	public void start() {
		if(look.tag == '(' || look.tag == Tag.NUM){
			expr();
			match(Tag.EOF);
		} else {
			error(String.format(ErrorMessages.ERROR, "start",look.tag));
		}
	}

	private void expr() {
		if(look.tag == '(' || look.tag == Tag.NUM){
			term();
			exprp();
		} else {
			error(String.format(ErrorMessages.ERROR, "expr",look.tag));
		}
	}

	private void exprp() {
		switch (look.tag){

			case '+':
			case '-':
				match(look.tag);
				term();
				exprp();
				break;	

			case ')':
			case Tag.EOF:
				break;

			default:
				error(String.format(ErrorMessages.ERROR, "exprp",look.tag));
		}
		
	}

	private void term() {
	// ... completare ...
		if(look.tag == '(' || look.tag == Tag.NUM){
			fact();
			termp();
		} else {
			error(String.format(ErrorMessages.ERROR, "term",look.tag));
		}
	}

	private void termp() {
	// ... completare ...
		if(look.tag == '*' || look.tag == '/'){
			match(look.tag);
			fact();
			termp();

		} 
			switch (look.tag){

			case '*':
			case '/':
				match(look.tag);
				fact();
				termp();
				break;	

			case Tag.EOF:
			case ')':
			case '+':
			case '-':
				break;

			default:
				error(String.format(ErrorMessages.ERROR, "termp",look.tag));
		}
	}

	private void fact() {
	// ... completare ...
		if(look.tag == Tag.NUM){
			match(look.tag);
		} 
		else if(look.tag == '('){
			match(look.tag);
			expr();
			if(look.tag != ')'){
				error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "fact",look.tag));
			}
			match(look.tag);
		} 
		else {
			error(String.format(ErrorMessages.ERROR, "fact",look.tag));
		}
	}

	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "prova.txt"; // il percorso del file da leggere

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			Parser parser = new Parser(lex, br);
			parser.start();
			System.out.println("Input OK");
			br.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}