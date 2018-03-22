package Parser.Nodes;

import Compiler.CompilerState;
import Compiler.SymbolTable;
import Errors.SyntaxError;
import Parser.Operators.PreunOp;
import Tokenizer.TokenReader;
import Tokenizer.Tokens.Token;

import java.util.Vector;

public class OptElse extends ASTNode {
    private ASTNode stmt;

    public OptElse() {
        stmt = null;
    }

    public void setStmt(ASTNode stmt) {
        this.stmt = stmt;
    }

    public ASTNode getStmt() {
        return stmt;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        String indentStr = super.getASTR(indentDepth);
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        OptElse optElse = new OptElse();
        if (tr.peek().getValue().equals("else")) {
            tr.read();

            optElse.setStmt(Statement.parse(cs, st));
            return optElse;
        }
        return null;
    }
}
