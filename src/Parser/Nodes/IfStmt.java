package Parser.Nodes;

import Compiler.CompilerState;
import Compiler.SymbolTable;
import Errors.SyntaxError;
import Parser.Operators.PreunOp;
import Tokenizer.TokenReader;
import Tokenizer.Tokens.Token;

import java.util.Vector;

public class IfStmt extends ASTNode {
    private ASTNode expr;
    private ASTNode stmt;
    private ASTNode optElse;

    public IfStmt() {
        expr = null;
        stmt = null;
        optElse = null;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    public ASTNode getExpr() {
        return expr;
    }

    public void setStmt(ASTNode stmt) {
        this.stmt = stmt;
    }

    public ASTNode getStmt() {
        return stmt;
    }

    public void setOptElse(ASTNode optElse) {
        this.optElse = optElse;
    }

    public ASTNode getOptElse() {
        return optElse;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        String indentStr = super.getASTR(indentDepth);
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        IfStmt ifStmt = new IfStmt();
        if (tr.peek().getValue().equals("if")) {
            tr.read();

            if (tr.peek().getValue().equals("(")) {
                ifStmt.setExpr(Expr.parse(cs, st));
            }
            else {
                throw new SyntaxError(tr.read(), "(");
            }

            if (tr.peek().getValue().equals(")")) {
                ifStmt.setStmt(Statement.parse(cs, st));
                ifStmt.setOptElse(OptElse.parse(cs, st));
            }
            else {
                throw new SyntaxError(tr.read(), ")");
            }
        }
        else {
            throw new SyntaxError(tr.read(), "if");
        }
        return ifStmt;
    }
}
