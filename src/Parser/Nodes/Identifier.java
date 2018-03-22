package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Tokenizer.Tokens.IdentifierToken;
import Tokenizer.Tokens.Token;
import Compiler.CompilerState;
import Compiler.SymbolTable;

public class Identifier extends ASTNode {
    private Token token;

    public Identifier(Token token) {
        this.token = token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String getASTR(int indentDepth) {
        return token.getValue();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        if (IdentifierToken.isToken(tr.peek())) {
            Identifier id = new Identifier(tr.read());
            st.setUsed(id.getToken());
            return id;
        }
        else {
            throw new SyntaxError(tr.read(), "IDENTIFIER");
        }
    }
}
