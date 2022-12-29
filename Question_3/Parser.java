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
		System.out.println( "token = " + look);
	}

	void error(String s) {
		throw new Error( "near line " + lex.line + ": " + s);
	}

	void match(int t) {
		if (look.tag == t) {
		if (look.tag != Tag.EOF) move();
		} else error(ErrorMessages.SYNTAX_ERROR);
	}


	private void expr() {
		// ... completare ...
		switch(look.tag){
		case '+':
		case '*':
			match(look.tag);
			if(look.tag == '('){
				match(look.tag);
				exprlist();
				if(look.tag == ')'){
					match(look.tag);
				} else {
					error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, look.tag));
				}
			} else {
				error(String.format(ErrorMessages.ERROR, "expr",look.tag));
			}
			break;

		case '-':
		case '/':
			match(look.tag);
			expr();
			expr();
			break;

		case Tag.ID:
		case Tag.NUM:
			match(look.tag);
			break;
		case Tag.EOF:
			match(look.tag);
			break;
		default:
			error(String.format(ErrorMessages.ERROR, "expr",look.tag));
		}

	}

	private void exprlist(){
	
		expr();
		exprlistp();

	
	}

	private void exprlistp(){
		if (look.tag == ',' ){
			match(look.tag);
			expr();
			exprlistp();
		}
	}

	private void prog(){
		if(
			Tag.ASSIGN == look.tag || 
			Tag.READ   == look.tag ||
			Tag.PRINT  == look.tag ||
			Tag.WHILE  == look.tag ||
			Tag.COND   == look.tag ||
			look.tag   == '{'
		){
			statlist();
			match(Tag.EOF);
		}else{
			error(String.format(ErrorMessages.ERROR, "prog",look.tag));
		}
	}

	private void statlist(){
		if(
			Tag.ASSIGN == look.tag || 
			Tag.READ   == look.tag ||
			Tag.PRINT  == look.tag ||
			Tag.WHILE  == look.tag ||
			Tag.COND   == look.tag ||
			look.tag   == '{'
		){
			stat();
			statlistp();
		}else{
			error(String.format(ErrorMessages.ERROR, "statlist",look.tag));
		}
		
	}

	private void statlistp(){
		if(look.tag == ';'){
			match(look.tag);
			stat();
			statlistp();
		}
	}

	private void stat(){
		switch (look.tag) {

			case Tag.ASSIGN:
				match(look.tag);
				expr();
				if(look.tag == Tag.TO){
					match(look.tag);
					idlist();
				} else {
					error(String.format(ErrorMessages.ERROR, "stat", look.tag));
				}
				break;

			case Tag.PRINT:
				match(look.tag);
				if(look.tag == '['){
					match(look.tag);
					exprlist();
					if(look.tag == ']'){
						match(look.tag);
					} else {
						error(String.format(ErrorMessages.MISSING_END_BRACKETS_ERROR, "stat", look.tag));
					}
				} else {
					error(String.format(ErrorMessages.ERROR, "stat", look.tag));
				}
				break;

			case Tag.READ:
				match(look.tag);
				if(look.tag == '['){
					match(look.tag);
					idlist();
					if(look.tag == ']'){
						match(look.tag);
					} else {
						error(String.format(ErrorMessages.MISSING_END_BRACKETS_ERROR, "stat", look.tag));
					}
				} else {
					error(String.format(ErrorMessages.ERROR, "stat", look.tag));
				}
				break;

			case Tag.WHILE:
				match(look.tag);
				if(look.tag == '('){
					match(look.tag);
					bexpr();
					if(look.tag == ')'){
						match(look.tag);
					} else {
						error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "stat", look.tag));
					}
					stat();

				} else {
					error(String.format(ErrorMessages.ERROR, "stat", look.tag));
				}
				break;

			case Tag.COND:
				match(look.tag);
				if(look.tag == '['){
						match(look.tag);
						optlist();
					if(look.tag == ']'){
						match(look.tag);
					} else {
						error(String.format(ErrorMessages.MISSING_END_BRACKETS_ERROR, "stat", look.tag));
					}

					if(look.tag == Tag.END){
						match(look.tag);
					} 
					else if(look.tag == Tag.ELSE){
						match(look.tag);
						stat();
						if(look.tag == Tag.END ){
							match(look.tag);
							
						}else{
							error(String.format(ErrorMessages.ERROR, "missing end statement with else", look.tag));
						} 
					} else {
							error(String.format(ErrorMessages.ERROR, "missing end statement", look.tag));
					}
					
				} else {
					error(String.format(ErrorMessages.ERROR, "stat", look.tag));
				}
				break;

			case '{' :
				match(look.tag);
				statlist();
				if(look.tag == '}'){
					match(look.tag);
				} else {
					error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "stat", look.tag));
				}
				break;
			default:
				error(String.format(ErrorMessages.ERROR, "stat", look.tag));
		}
	}	

	private void idlist(){
		if(look.tag == Tag.ID){
			match(look.tag);
			idlistp();
		} else {
			error(String.format(ErrorMessages.ERROR, "idlist", look.tag));
		}
		
	}

	private void idlistp(){
		if (
			look.tag == ','
		){
			match(look.tag);
			if(look.tag == Tag.ID){
				match(look.tag);
				idlistp();
			}
		}
	}

	private void optlist(){
		if(look.tag == Tag.OPTION){
			optitem();
			optlistp();
		}else{
			error(String.format(ErrorMessages.ERROR, "optlist", look.tag));
		}
		
	}

	private void optlistp(){
		if(look.tag == Tag.OPTION ){ 
			optitem();
			optlistp();
		}
	}

	private void optitem(){
		if(look.tag == Tag.OPTION){
			match(look.tag);
			if(look.tag == '('){
				match(look.tag);
				bexpr();
				if(look.tag == ')'){
					match(look.tag);
					if(look.tag == Tag.DO){
						match(look.tag);
						stat();
					} else {
						error("Missing DO Tag! Found " + look.tag);
					}
				} else {
					error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "optitem", look.tag));
				}
			}  else {
				error("Missing Opening Paranthesis! Found " + look.tag);
			}
		} else {
			error(String.format(ErrorMessages.ERROR, "optitem", look.tag));
		}
	}

	private void bexpr(){
		if(look.tag == Tag.RELOP){
			match(look.tag);
			expr();
			expr();
		} else {
			error(String.format(ErrorMessages.ERROR, "bexpr", look.tag));
		}
	}


	public static void main(String[] args) {
		Lexer lex = new Lexer();
		String path = "error1.txt";
		try {
		BufferedReader br = new BufferedReader(new FileReader(path));
		Parser parser = new Parser(lex, br);
		parser.prog();
		System.out.println("Input OK");
		br.close();
		} catch (IOException e) {e.printStackTrace();}
	}	
}