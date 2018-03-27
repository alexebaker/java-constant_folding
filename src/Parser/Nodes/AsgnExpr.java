package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.PointerType;
import Types.Type;

public class AsgnExpr extends ASTNode {
    private ASTNode asgnExpr;
    private ASTNode condExpr;

    public AsgnExpr() {
        this.asgnExpr = null;
        this.condExpr = null;
    }

    public void setAsgnExpr(ASTNode asgnExpr) {
        this.asgnExpr = asgnExpr;
    }

    public void setCondExpr(ASTNode condExpr) {
        this.condExpr = condExpr;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        if (condExpr != null) {
            if (asgnExpr != null) {
                str.append(getTypePrefix());
                str.append("(");
                str.append(condExpr.getASTR(indentDepth));
                str.append("=");
                str.append(asgnExpr.getASTR(indentDepth));
                str.append(")");
            }
            else {
                str.append(condExpr.getASTR(indentDepth));
            }
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        AsgnExpr asgnExpr = new AsgnExpr();
        asgnExpr.setCondExpr(CondExpr.parse(cs, st));
        if (tr.peek().getValue().equals("=")) {
            tr.read();
            asgnExpr.setAsgnExpr(AsgnExpr.parse(cs, st));
        }
        return asgnExpr;
    }

    public Type getNodeType() {
        if (getType() == null) {
            if (asgnExpr == null) {
                setType(condExpr.getNodeType());
            }
            else {
                Type lhs = condExpr.getNodeType();
                Type rhs = asgnExpr.getNodeType();
                if (PointerType.isType(lhs) && PointerType.isType(rhs)) {
                    if (lhs.equals(rhs)) {
                        setType(lhs);
                    }
                    else {
                        //exception
                    }
                }
                else if (Types.PrimType.isType(lhs) && Types.PrimType.isType(rhs)) {
                    setType(lhs);
                }
                else {
                    //exception
                }
            }
        }
        return getType();
    }

    public ASTNode foldConstants() {
        if (condExpr != null) {
            condExpr = condExpr.foldConstants();
            if (asgnExpr != null) {
                asgnExpr = asgnExpr.foldConstants();
            }
        }
        return this;
    }
}
