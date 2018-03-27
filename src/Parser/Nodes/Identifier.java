package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Tokenizer.Tokens.IdentifierToken;
import Tokenizer.Tokens.Token;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Compiler.VDI;
import Types.Type;

public class Identifier extends ASTNode {
    private Token token;
    private VDI vdi;

    public Identifier(Token token, VDI vdi) {
        this.token = token;
        this.vdi = vdi;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setVdi(VDI vdi) {
        this.vdi = vdi;
    }

    public VDI getVdi() {
        return vdi;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        str.append(getTypePrefix());
        str.append(token.getValue());
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        if (IdentifierToken.isToken(tr.peek())) {
            Token token = tr.read();
            st.setUsed(token);
            return new Identifier(token, st.getVDI(token));
        }
        else {
            throw new SyntaxError(tr.read(), "IDENTIFIER");
        }
    }

    public Type getNodeType() {
        if (getType() == null) {
            setType(getVdi().getType());
        }
        return getType();
    }

    public ASTNode foldConstants() {
        return this;
    }
}
