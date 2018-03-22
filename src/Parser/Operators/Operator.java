package Parser.Operators;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;

public class Operator extends ASTNode {
    private Token op;
    private ASTNode lhs;
    private ASTNode rhs;

    public Operator(Token op) {
        this.op = op;
        this.lhs = null;
        this.rhs = null;
    }

    public ASTNode getLhs() {
        return lhs;
    }

    public ASTNode getRhs() {
        return rhs;
    }

    public void setLhs(ASTNode lhs) {
        this.lhs = lhs;
    }

    public void setRhs(ASTNode rhs) {
        this.rhs = rhs;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");

        str.append("(");
        if (lhs != null) str.append(lhs.getASTR(indentDepth));
        str.append(op.getValue());
        if (rhs != null) str.append(rhs.getASTR(indentDepth));
        str.append(")");
        return str.toString();
    }
}
