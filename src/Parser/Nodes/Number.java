package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Tokenizer.Tokens.NumberToken;
import Tokenizer.Tokens.Token;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.PrimType;
import Types.Type;
import Types.TypeEnum;

public class Number extends ASTNode {
    private Token token;

    public Number(Token token) {
        this.token = token;
    }

    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        str.append(getTypePrefix());
        str.append(token.getValue());
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        if (NumberToken.isToken(tr.peek())) {
            return new Number(tr.read());
        }
        else {
            throw new SyntaxError(tr.read(), "NUMBER");
        }
    }

    public Type getNodeType() {
        if (getType() == null) {
            setType(new PrimType(TypeEnum.SIGNED));
        }
        return getType();
    }

    public ASTNode foldConstants() {
        return this;
    }
}
