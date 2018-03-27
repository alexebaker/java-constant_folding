package Parser.Nodes;

import Errors.SyntaxError;
import Parser.Operators.EqOp;
import Parser.Operators.Operator;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.Type;

public class EqExpr extends ASTNode {
    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        ASTNode node = RelExpr.parse(cs, st);
        while (EqOp.isOp(tr.peek())) {
            Operator temp = new EqOp(tr.read());
            temp.setLhs(node);
            temp.setRhs(RelExpr.parse(cs, st));
            node = temp;
        }
        return node;
    }

    public Type getNodeType() {
        return getType();
    }

    public ASTNode foldConstants() {
        return this;
    }
}
