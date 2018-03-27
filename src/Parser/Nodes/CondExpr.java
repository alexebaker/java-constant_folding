package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.Type;
import Types.TypeEnum;

public class CondExpr extends ASTNode {
    private ASTNode logOrExpr;
    private ASTNode expr;
    private ASTNode condExpr;

    public CondExpr() {
        logOrExpr = null;
        expr = null;
        condExpr = null;
    }

    public void setLogOrExpr(ASTNode logOrExpr) {
        this.logOrExpr = logOrExpr;
    }

    public void setCondExpr(ASTNode condExpr) {
        this.condExpr = condExpr;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        if (logOrExpr != null) {
            if (expr != null && condExpr != null) {
                str.append(getTypePrefix());
                str.append("(");
                str.append(logOrExpr.getASTR(0));
                str.append("?");
                str.append(expr.getASTR(0));
                str.append(":");
                str.append(condExpr.getASTR(0));
                str.append(")");
            }
            else {
                str.append(logOrExpr.getASTR(0));
            }
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        CondExpr condExpr = new CondExpr();
        condExpr.setLogOrExpr(LogOrExpr.parse(cs, st));
        if (tr.peek().getValue().equals("?")) {
            tr.read();
            condExpr.setExpr(Expr.parse(cs, st));
            if (tr.peek().getValue().equals(":")) {
                tr.read();
                condExpr.setCondExpr(CondExpr.parse(cs, st));
            }
            else {
                throw new SyntaxError(tr.read(), ":");
            }
        }
        return condExpr;
    }


    public Type getNodeType() {
        if (getType() == null) {
            if (expr != null && condExpr != null) {
                if (logOrExpr.getNodeType().getTypeEnum() == TypeEnum.BOOL) {
                    if (expr.getNodeType().equals(condExpr.getNodeType())) {
                        setType(expr.getNodeType());
                    } else {
                        //excpetion
                    }
                } else {
                    //exception
                }
            }
            else {
                setType(logOrExpr.getNodeType());
            }
        }
        return getType();
    }

    public ASTNode foldConstants() {
        logOrExpr = logOrExpr.foldConstants();
        if (expr != null && condExpr != null) {
            expr = expr.foldConstants();
            condExpr.foldConstants();
        }
        return this;
    }
}
