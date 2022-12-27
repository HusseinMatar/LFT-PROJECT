import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    private void skipSpaces(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
    }


    private char peekChar(BufferedReader br){
        char ch = (char) -1;
        try {
            br.mark(1); //marks the current position
            ch = (char) br.read();
            br.reset(); //it will be repositioned after calling
        } catch(IOException ex){
            ch = (char) -1;
        }
        return ch;
    }

    private void skipSingleLineComment(BufferedReader br){
        while(peek != '\n' && peek != (char) -1){
            readch(br);
        }
        skipSpaces(br);
    }

    private void skipMultipleLinesComment(BufferedReader br){
        readch(br);
        readch(br); //to surpass /*
        while(!(peek == '*' && peekChar(br) == '/') && peek != (char) -1 ){
            if(peek == '\n') line ++;
            readch(br);
        }

        if( peek == (char) -1){
            throw new RuntimeException("Error: The comment isn't closed correctly!");
        }

        readch(br);
        readch(br);
        skipSpaces(br);
    }

    public Token lexical_scan(BufferedReader br) {

        skipSpaces(br);
        
        if(peek == '/' && peekChar(br) == '/'){
            skipSingleLineComment(br);
        }
        if(peek == '/' && peekChar(br) == '*'){
            skipMultipleLinesComment(br);
        }


        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

	// ... gestire i casi di ( ) [ ] { } + - * / ; , ... //

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '[':
                peek = ' ';
                return Token.lpq;

            case ']':
                peek = ' ';
                return Token.rpq;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            case '/':
                peek = ' ';
                return Token.div; 
                

            case ';':
                peek = ' ';
                return Token.semicolon;

            case ',':
                peek = ' ';
                return Token.comma;


 //----------------------------------------------------------------
	
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

	// ... gestire i casi di || < > <= >= == <> ... //

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }


            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else  if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else {
                    return Word.lt;
                }

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else {
                    return Word.gt;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("Erroneous character" + " after = : " + peek);
                    return null;
                }      
//------------------------------------------------------------------- 
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if(Character.isLetter(peek)) {
	// ... gestire il caso degli identificatori e delle parole chiave //
                    
                    String lexeme = Character.toString(peek);
                    readch(br);
                    while(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_'){
                    lexeme += Character.toString(peek);
                    readch(br);
                    }

                    if(lexeme.equals("while")){
                        return Word.whiletok;
                    }else if(lexeme.equals("read")){
                        return Word.read;
                    }else if(lexeme.equals("print")){
                        return Word.print;
                    }else if(lexeme.equals("do")){
                        return Word.dotok;
                    }else if(lexeme.equals("else")){
                        return Word.elsetok;
                    }else if(lexeme.equals("conditional")){
                        return Word.conditional;
                    }else if(lexeme.equals("option")){
                        return Word.option;
                    }else if(lexeme.equals("begin")){
                        return Word.begin;
                    }else if(lexeme.equals("end")){
                        return Word.end;   
                    }
                    else if(lexeme.equals("assign")){
                        return Word.assign;
                    }else if(lexeme.equals("to")){
                        return Word.to;
                    }else{
                        return new Word(Tag.ID, lexeme);
                    }

                }  else if(peek == '_'){
                    String lexeme = Character.toString(peek);
                    readch(br);
                    while(peek == '_'){
                        lexeme += Character.toString(peek);
                        readch(br);
                    }
                    
                    if(peek != ' ' && !Character.isDigit(peek) && !Character.isLetter(peek)){
                        System.err.println("Identifier can't be composed by only (_)");
                        return null;
                    } 

                    while(Character.isLetter(peek) || Character.isDigit(peek)){
                        lexeme += Character.toString(peek);
                        readch(br);
                    } 

                    return new Word(Tag.ID, lexeme);
                }

                else if (Character.isDigit(peek)) {


	// ... gestire il caso dei numeri ... //
                    String lexeme = Character.toString(peek);
                    readch(br);
                    while(Character.isDigit(peek)){
                        lexeme += Character.toString(peek);
                        readch(br);
                    }
                    if(Character.isLetter(peek) || peek == '_'){
                        System.err.println("Identifier can't start with a digit.");
                        return null;
                    }
                    return new NumberTok(Integer.parseInt(lexeme));
                    }  
                    else {
                        System.err.println("Erroneous character: "
                                + peek );
                        return null;
                    }
         }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}
