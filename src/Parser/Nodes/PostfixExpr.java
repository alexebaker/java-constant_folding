package Parser.Nodes;

import Errors.SyntaxError;
import Parser.Operators.Operator;
import Parser.Operators.PostunOp;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.PointerType;
import Types.Type;

public class PostfixExpr extends ASTNode {
    private ASTNode primaryExpr;
    private ASTNode postfixExpr;
    private ASTNode arraySpec;

    public PostfixExpr() {
        this.primaryExpr = null;
        this.postfixExpr = null;
        this.arraySpec = null;
    }

    public void setPostfixExpr(ASTNode postfixExpr) {
        this.postfixExpr = postfixExpr;
    }

    public void setArraySpec(ASTNode arraySpec) {
        this.arraySpec = arraySpec;
    }

    public void setPrimaryExpr(ASTNode primaryExpr) {
        this.primaryExpr = primaryExpr;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        if (primaryExpr != null) {
            str.append(getTypePrefix());
            str.append("(");
            str.append(primaryExpr.getASTR(indentDepth));

            if (arraySpec != null) {
                str.append(arraySpec.getASTR(indentDepth));
            }

            if (postfixExpr != null) {
                str.append(postfixExpr.getASTR(indentDepth));
            }
            str.append(")");
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        ASTNode node = null;
        if (PrimaryExpr.beginsPrimaryExpr(tr.peek())) {
            node = PrimaryExpr.parse(cs, st);
            while (PostunOp.isOp(tr.peek()) || tr.peek().getValue().equals("[")) {
                if (PostunOp.isOp(tr.peek())) {
                    Operator temp = new PostunOp(tr.read());
                    temp.setLhs(node);
                    temp.setRhs(PostfixExpr.parse(cs, st));
                    node = temp;
                } else {
                    PostfixExpr temp = new PostfixExpr();
                    temp.setPrimaryExpr(node);
                    temp.setArraySpec(ArraySpec.parse(cs, st));
                    temp.setPostfixExpr(PostfixExpr.parse(cs, st));
                    node = temp;
                }
            }
        }
        return node;
    }

    public Type getNodeType() {
        if (getType() == null) {
            if (primaryExpr != null) {
                Type type = primaryExpr.getNodeType();
                if (arraySpec != null) {
                    type = type.deRef();
                }

                if (postfixExpr != null) {
                    Type tmp = postfixExpr.getNodeType();
                    tmp.setOfType(type);
                    type = tmp;
                }

                setType(type);
            }
        }
        return getType();
    }

}
