import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count = 0;

    public Translator(Lexer l, BufferedReader br) {
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
            if (look.tag != Tag.EOF)
                move();
        } else {
            System.out.println("here else " + t + " " + look.tag);
            error(ErrorMessages.SYNTAX_ERROR);
        }
    }

    public void prog() {
        if (Tag.ASSIGN == look.tag ||
                Tag.READ == look.tag ||
                Tag.PRINT == look.tag ||
                Tag.WHILE == look.tag ||
                Tag.COND == look.tag ||
                look.tag == '{') {
            int lnext_prog = code.newLabel();
            System.out.println("here" + lnext_prog);
            statlist(lnext_prog);
            System.out.println("here1 " + lnext_prog);
            code.emitLabel(lnext_prog);
            match(Tag.EOF);
            try {
                code.toJasmin();
            } catch (java.io.IOException e) {
                System.out.println("IO error\n");
            }
        } else {
            error(String.format(ErrorMessages.ERROR, "prog", look.tag));
        }
    }

    private void statlist(int lnext_prog) {
        if (Tag.ASSIGN == look.tag ||
                Tag.READ == look.tag ||
                Tag.PRINT == look.tag ||
                Tag.WHILE == look.tag ||
                Tag.COND == look.tag ||
                look.tag == '{') {
            int l_statlist = code.newLabel();
            stat(l_statlist);
            code.emitLabel(l_statlist);
            statlistp(lnext_prog);
        } else {
            error(String.format(ErrorMessages.ERROR, "statlist", look.tag));
        }

    }

    private void statlistp(int lnext_prog) {
        if (look.tag == ';') {
            match(look.tag);
            int l_statlistp = code.newLabel();
            stat(l_statlistp);
            code.emitLabel(l_statlistp);
            statlistp(lnext_prog);
        }
    }

    public void stat(int l_next) {
        int l_true, l_false, begin;

        switch (look.tag) {
            case Tag.ASSIGN:
                match(look.tag);
                expr();
                if (look.tag == Tag.TO) {
                    match(look.tag);
                    idlist(l_next);
                    code.emit(OpCode.istore, l_next);
                } else {
                    error(String.format(ErrorMessages.ERROR, "stat", look.tag));
                }
                break;

            case Tag.PRINT:
                match(look.tag);
                if (look.tag == '[') {
                    match(look.tag);
                    exprlist();
                    if (look.tag == ']') {
                        match(look.tag);
                        code.emit(OpCode.invokestatic, 1);
                    } else {
                        error(String.format(ErrorMessages.MISSING_END_BRACKETS_ERROR, "stat", look.tag));
                    }
                } else {
                    error(String.format(ErrorMessages.ERROR, "stat", look.tag));
                }
                break;

            case Tag.READ:
                match(look.tag);
                if (look.tag == '[') {
                    match(look.tag);
                    idlist(l_next);
                    if (look.tag == ']') {
                        match(look.tag);
                        code.emit(OpCode.invokestatic, 0);
                        code.emit(OpCode.istore, l_next);
                    } else {
                        error(String.format(ErrorMessages.MISSING_END_BRACKETS_ERROR, "stat", look.tag));
                    }
                } else {
                    error(String.format(ErrorMessages.ERROR, "stat", look.tag));
                }
                break;

            case Tag.WHILE:
                match(look.tag);
                if (look.tag == '(') {
                    match(look.tag);
                    begin = code.newLabel();
                    l_true = code.newLabel();
                    l_false = l_next;
                    code.emitLabel(begin);
                    bexpr(l_true, l_false);
                    if (look.tag == ')') {
                        match(look.tag);
                        code.emitLabel(l_true);

                    } else {
                        error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "stat", look.tag));
                    }
                    stat(l_next);
                    code.emit(OpCode.GOto, begin);
                    code.emitLabel(l_false);

                } else {
                    error(String.format(ErrorMessages.ERROR, "stat", look.tag));
                }
                break;

            case '{':
                match(look.tag);
                statlist(l_next);
                if (look.tag == '}') {
                    match(look.tag);
                } else {
                    error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "stat", look.tag));
                }
                break;

            case Tag.COND:
                match(look.tag);
                if (look.tag == '[') {
                    match(look.tag);
                    optlist();
                    if (look.tag == ']') {
                        match(look.tag);
                    } else {
                        error(String.format(ErrorMessages.MISSING_END_BRACKETS_ERROR, "stat", look.tag));
                    }

                    if (look.tag == Tag.END) {
                        match(look.tag);
                    } else if (look.tag == Tag.ELSE) {
                        match(look.tag);
                        stat(l_next);
                        if (look.tag == Tag.END) {
                            match(look.tag);

                        } else {
                            error(String.format(ErrorMessages.ERROR, "missing end statement with else", look.tag));
                        }
                    } else {
                        error(String.format(ErrorMessages.ERROR, "missing end statement", look.tag));
                    }

                } else {
                    error(String.format(ErrorMessages.ERROR, "stat", look.tag));
                }
                break;

            default:
                error(String.format(ErrorMessages.ERROR, "stat", look.tag));

        }
    }

    private void idlist(int l_next) {
        if (look.tag == Tag.ID) {
            int id_addr = st.lookupAddress(((Word) look).lexeme);
            if (id_addr == -1) {
                id_addr = count;
                st.insert(((Word) look).lexeme, count++);
            }
            match(Tag.ID);
        } else {
            error(String.format(ErrorMessages.ERROR, "idlist", look.tag));
        }
    }

    private void expr( /* completare */ ) {
        switch (look.tag) {
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;

            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;

            case '+':
                match(look.tag);
                if (look.tag == '(') {
                    match(look.tag);
                    exprlist();
                    if (look.tag == ')') {
                        match(look.tag);
                        code.emit(OpCode.iadd);
                    } else {
                        error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, look.tag));
                    }
                } else {
                    error(String.format(ErrorMessages.ERROR, "expr", look.tag));
                }
                break;

            case '*':
                match(look.tag);
                if (look.tag == '(') {
                    match(look.tag);
                    exprlist();
                    if (look.tag == ')') {
                        match(look.tag);
                        code.emit(OpCode.imul);
                    } else {
                        error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, look.tag));
                    }
                } else {
                    error(String.format(ErrorMessages.ERROR, "expr", look.tag));
                }
                break;

            case Tag.ID:
            case Tag.NUM:
                match(look.tag);
                break;
            case Tag.EOF:
                match(look.tag);
                break;
            default:
                error(String.format(ErrorMessages.ERROR, "expr", look.tag));

        }
    }

    private void bexpr(int l_true, int l_false) {
        if (look.tag == Tag.RELOP) {
            if (look == Word.eq) {
                match(look.tag);
                expr();
                expr();
                code.emit(OpCode.if_icmpeq, l_true);
                code.emit(OpCode.GOto, l_false);
            } else if (look == Word.lt) {
                match(look.tag);
                expr();
                expr();
                code.emit(OpCode.if_icmplt, l_true);
                code.emit(OpCode.GOto, l_false);
            } else if (look == Word.le) {
                match(look.tag);
                expr();
                expr();
                code.emit(OpCode.if_icmple, l_true);
                code.emit(OpCode.GOto, l_false);
            } else if (look == Word.ge) {
                match(look.tag);
                expr();
                expr();
                code.emit(OpCode.if_icmpge, l_true);
                code.emit(OpCode.GOto, l_false);
            } else if (look == Word.gt) {
                match(look.tag);
                expr();
                expr();
                code.emit(OpCode.if_icmpgt, l_true);
                code.emit(OpCode.GOto, l_false);
            } else if (look == Word.ne) {
                match(look.tag);
                expr();
                expr();
                code.emit(OpCode.if_icmpne, l_true);
                code.emit(OpCode.GOto, l_false);
            } else {
                error("Invalid Boolean Exp. " + look);
            }
        } else {
            error(String.format(ErrorMessages.ERROR, "bexpr", look.tag));
        }
    }

    private void exprlistp() {
        if (look.tag == ',') {
            match(look.tag);
            expr();
            exprlistp();
        }
    }

    private void exprlist() {
        expr();
        exprlistp();
    }

    private void idlistp() {
        if (look.tag == ',') {
            match(look.tag);
            if (look.tag == Tag.ID) {
                match(look.tag);
                idlistp();
            }
        }
    }

    private void optlist() {
        if (look.tag == Tag.OPTION) {
            optitem();
            optlistp();
        } else {
            error(String.format(ErrorMessages.ERROR, "optlist", look.tag));
        }

    }

    private void optlistp() {
        if (look.tag == Tag.OPTION) {
            optitem();
            optlistp();
        }
    }

    private void optitem() {
        if (look.tag == Tag.OPTION) {
            match(look.tag);
            if (look.tag == '(') {
                match(look.tag);
                bexpr(0, 0);
                if (look.tag == ')') {
                    match(look.tag);
                    if (look.tag == Tag.DO) {
                        match(look.tag);
                        stat(0);
                    } else {
                        error("Missing DO Tag! Found " + look.tag);
                    }
                } else {
                    error(String.format(ErrorMessages.MISSING_END_PARANTHESIS_ERROR, "optitem", look.tag));
                }
            } else {
                error("Missing Opening Paranthesis! Found " + look.tag);
            }
        } else {
            error(String.format(ErrorMessages.ERROR, "optitem", look.tag));
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.lft";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}