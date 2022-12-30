import java.io.*;

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;


    public Valutatore(Lexer l, BufferedReader br) {
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
        int expr_val;
        
        if(look.tag == '(' || look.tag == Tag.NUM){
            expr_val = expr();
            match(Tag.EOF);
            System.out.println(expr_val);
        } else {
			error(String.format(ErrorMessages.ERROR, "start",look.tag));
		}
        
    }

    private int expr() {
        int term_val = 0, exprp_val = 0;

        if(look.tag == '(' || look.tag == Tag.NUM){
            term_val = term();
            exprp_val = exprp(term_val);
        } else {
			error(String.format(ErrorMessages.ERROR, "expr",look.tag));
		}

        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val = 0, exprp_val=0;
        switch (look.tag){

			case '+':
				match(look.tag);
				term_val = term();
                exprp_val = exprp(exprp_i + term_val);
				break;	

            case '-':
                 match(look.tag);
				term_val = term();
                exprp_val = exprp(exprp_i - term_val);
				break;

			case ')':
			case Tag.EOF:
                exprp_val = exprp_i;
				break;   

			default:
				error(String.format(ErrorMessages.ERROR, "exprp",look.tag));
		}
        return exprp_val;
    }

    private int term() {
        int fact_val = 0 , termp_val = 0;
        if(look.tag == '(' || look.tag == Tag.NUM){
			fact_val = fact();
			termp_val = termp(fact_val);
		} else {
			error(String.format(ErrorMessages.ERROR, "term",look.tag));
		}
        return termp_val;
    }

    private int termp(int termp_i) {
        int fact_val=0;
        int termp_val=0;
    	switch (look.tag){

			case '*':
				match(look.tag);
				fact_val = fact();
				termp_val = termp(fact_val * termp_i);
				break;

            case '/':
                 match(look.tag);
				fact_val = fact();
				termp_val = termp(termp_i / fact_val);
				break;


			case Tag.EOF:
			case ')':
			case '+':
			case '-':
                termp_val = termp_i;
				break;

			default:
				error(String.format(ErrorMessages.ERROR, "termp",look.tag));
		}
        return termp_val;
    }

    private int fact() {
        int fact_val=0;

        if(look.tag == Tag.NUM){
            fact_val= ((NumberTok)look).lexeme;
			match(look.tag);    
		} 
		else if(look.tag == '('){
			match(look.tag);
			fact_val = expr();
			if(look.tag != ')'){
				error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "fact",look.tag));
			} 
            else {
			match(look.tag);
            }
		} 
		else {
			error(String.format(ErrorMessages.ERROR, "fact",look.tag));
		}

        return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}