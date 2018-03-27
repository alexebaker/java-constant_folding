package Parser.Nodes;

import Errors.SyntaxError;
import Parser.Operators.FactorOp;
import Parser.Operators.Operator;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.Type;

public class Term extends ASTNode {
    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        ASTNode node = Factor.parse(cs, st);
        while (FactorOp.isOp(tr.peek())) {
            Operator temp = new FactorOp(tr.read());
            temp.setLhs(node);
            temp.setRhs(Factor.parse(cs, st));
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
