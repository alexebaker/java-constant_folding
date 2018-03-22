package Parser.Nodes;

import Errors.SyntaxError;
import Parser.Operators.Operator;
import Parser.Operators.TermOp;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;

public class SimpleExpr extends ASTNode {
    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        ASTNode node = Term.parse(cs, st);
        while (TermOp.isOp(tr.peek())) {
            Operator temp = new TermOp(tr.read());
            temp.setLhs(node);
            temp.setRhs(Term.parse(cs, st));
            node = temp;
        }
        return node;
    }
}
