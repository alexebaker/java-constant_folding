package Compiler;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;

public class VDI {
    private Token name;
    private String status;
    private ASTNode type;

    public VDI(Token name, String status, ASTNode type) {
        this.name = name;
        this.status = status;
        this.type = type;
    }

    public Token getName() {
        return name;
    }

    public ASTNode getType() {
        return type;
    }

    public void setType(ASTNode type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        str.append(name);
        str.append(" ");
        str.append(status);
        str.append(" ");

        if (type != null) {
            str.append(type.getASTR(0));
        }
        else {
            str.append("unknown");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VDI) {
            return equals((VDI) obj);
        }
        return false;
    }

    public boolean equals(VDI vdi) {
        return name.equals(vdi.getName()) && status.equals(vdi.getStatus()) && type.getASTR(0).equals(vdi.getType().getASTR(0));
    }

    @Override
    public int hashCode() {
        return (name.hashCode() * status.hashCode() - type.getASTR(0).hashCode()) * 27;
    }
}
