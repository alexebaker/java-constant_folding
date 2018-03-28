package Parser.Nodes;

import Errors.SyntaxError;
import Parser.Operators.Operator;
import Parser.Operators.PreunOp;
import Tokenizer.TokenReader;
import Compiler.*;
import Types.Type;

public class Factor extends ASTNode {
    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        if (PreunOp.isOp(tr.peek())) {
            Operator op = new PreunOp(tr.read());
            op.setRhs(Factor.parse(cs, st));
            return op;
        }
        return PostfixExpr.parse(cs, st);
    }

    public Type getNodeType(CompilerState cs) {
        return getType();
    }

    public ASTNode foldConstants() {
        return this;
    }

    public Object getValue() {
        return null;
    }

    public Location getLocation() {
        return null;
    }
}
