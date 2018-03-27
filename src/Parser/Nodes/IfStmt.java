package Parser.Nodes;

import Compiler.CompilerState;
import Compiler.SymbolTable;
import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Types.Type;


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
        str.append("if (");
        str.append(expr.getASTR(0));
        str.append(")\n");
        str.append(stmt.getASTR(indentDepth+1));
        if (optElse != null) {
            str.append(optElse.getASTR(indentDepth));
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        IfStmt ifStmt = new IfStmt();
        if (tr.peek().getValue().equals("if")) {
            tr.read();

            if (tr.peek().getValue().equals("(")) {
                tr.read();
                ifStmt.setExpr(Expr.parse(cs, st));
            }
            else {
                throw new SyntaxError(tr.read(), "(");
            }

            if (tr.peek().getValue().equals(")")) {
                tr.read();
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

    public Type getNodeType() {
        if (getType() == null) {
            expr.getNodeType();
            stmt.getNodeType();
            if (optElse != null) optElse.getNodeType();
        }
        return getType();
    }

    public ASTNode foldConstants() {
        expr = expr.foldConstants();
        stmt = stmt.foldConstants();
        if (optElse != null) {
            optElse = optElse.foldConstants();
        }
        return this;
    }
}
