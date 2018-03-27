package Parser.Nodes;

import Compiler.CompilerState;
import Compiler.SymbolTable;
import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Types.Type;


public class WhileStmt extends ASTNode {
    private ASTNode expr;
    private ASTNode stmt;

    public WhileStmt() {
        expr = null;
        stmt = null;
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

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        str.append("while (");
        str.append(expr.getASTR(0));
        str.append(")\n");
        str.append(stmt.getASTR(indentDepth+1));
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        WhileStmt whileStmt = new WhileStmt();
        if (tr.peek().getValue().equals("while")) {
            tr.read();

            if (tr.peek().getValue().equals("(")) {
                tr.read();
                whileStmt.setExpr(Expr.parse(cs, st));
            }
            else {
                throw new SyntaxError(tr.read(), "(");
            }

            if (tr.peek().getValue().equals(")")) {
                tr.read();
                whileStmt.setStmt(Statement.parse(cs, st));
            }
            else {
                throw new SyntaxError(tr.read(), ")");
            }
        }
        else {
            throw new SyntaxError(tr.read(), "while");
        }
        return whileStmt;
    }

    public Type getNodeType() {
        if (getType() == null) {
            expr.getNodeType();
            stmt.getNodeType();
        }
        return getType();
    }

}
