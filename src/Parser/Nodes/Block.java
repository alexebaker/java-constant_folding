package Parser.Nodes;

import Compiler.CompilerState;
import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Compiler.SymbolTable;
import Tokenizer.Tokens.EOFToken;
import Tokenizer.Tokens.Token;

import java.util.Vector;


public class Block extends ASTNode {
    private Vector<ASTNode> defs;
    private Vector<ASTNode> stmts;
    private SymbolTable symbolTable;

    public Block() {
        this(null);
    }

    public Block(SymbolTable st) {
        super();
        defs = new Vector<>();
        stmts = new Vector<>();
        symbolTable = new SymbolTable(st);
    }

    public void addDef(ASTNode def) {
        defs.add(def);
    }

    public void addStmt(ASTNode stmt) {
        stmts.add(stmt);
    }

    public Vector<ASTNode> getDefs() {
        return defs;
    }

    public Vector<ASTNode> getStmts() {
        return stmts;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    @Override
    public String getVSR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        str.append(symbolTable.getVSR(indentDepth));
        str.append("\n");
        return str.toString();
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        for (ASTNode stmt : stmts) {
            if (stmt != null) str.append(stmt.getASTR(indentDepth));
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (ASTNode def : defs) {
            if (def != null) str.append(def);
        }
        for (ASTNode stmt : stmts) {
            if (stmt != null) str.append(stmt);
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) {
        TokenReader tr = cs.getTr();
        Block block = new Block(st);

        block.getSymbolTable().setInDef(true);
        while (Def.beginsDef(tr.peek())) {
            try {
                block.addDef(Def.parse(cs, block.getSymbolTable()));
            }
            catch (SyntaxError ex) {
                cs.addError(ex);
                Token recoveredToken = tr.recoverFromError();
                if (!recoveredToken.getValue().equals(";")) {
                    cs.addError(new SyntaxError(recoveredToken, ";"));
                    return block;
                }
            }
        }

        block.getSymbolTable().setInDef(false);
        while (Statement.beginsStmt(tr.peek())) {
            try {
                block.addStmt(Statement.parse(cs, block.getSymbolTable()));
            }
            catch (SyntaxError ex) {
                cs.addError(ex);
                if (EOFToken.isToken(ex.getToken())) {
                    return block;
                }
            }
        }
        return block;
    }
}
