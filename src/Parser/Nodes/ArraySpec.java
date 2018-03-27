package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.ArrayType;
import Types.PointerType;
import Types.Type;

public class ArraySpec extends ASTNode {
    private ASTNode expr;

    public ArraySpec() {
        this.expr = null;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    @Override
    public Type getNodeType() {
        if (getType() == null) {
            Type type = null;
            if (expr != null) {
                if (Types.PrimType.isType(expr.getNodeType())) {
                    type = new ArrayType(0);
                }
                else {
                    //exception
                }
            }
            else {
                type = new PointerType();
            }
            setType(type);
        }
        return getType();
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        str.append("[");
        if (expr != null) {
            //str.append(expr.getTypePrefix());
            str.append(expr.getASTR(indentDepth));
        }
        str.append("]");
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        if (tr.peek().getValue().equals("[")) {
            tr.read();
            ArraySpec arraySpec = new ArraySpec();
            if (tr.peek().getValue().equals("]")) {
                tr.read();
                return arraySpec;
            }
            else {
                arraySpec.setExpr(Expr.parse(cs, st));
                if (tr.peek().getValue().equals("]")) {
                    tr.read();
                    return arraySpec;
                }
                else {
                    throw new SyntaxError(tr.read(), "]");
                }
            }
        }
        else {
            throw new SyntaxError(tr.read(), "[");
        }
    }

    public ASTNode foldConstants() {
        if (expr != null) {
            expr = expr.foldConstants();
        }
        return this;
    }
}
