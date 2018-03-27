package Parser.Nodes;

import Errors.SyntaxError;
import Parser.Operators.Operator;
import Parser.Operators.RelOp;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.Type;

public class RelExpr extends ASTNode {
    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        ASTNode node = SimpleExpr.parse(cs, st);
        while (RelOp.isOp(tr.peek())) {
            Operator temp = new RelOp(tr.read());
            temp.setLhs(node);
            temp.setRhs(SimpleExpr.parse(cs, st));
            node = temp;
        }
        return node;
    }

    public Type getNodeType() {
        return getType();
    }

}
