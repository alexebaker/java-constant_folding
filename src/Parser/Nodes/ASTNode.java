package Parser.Nodes;

import Types.Type;


public abstract class ASTNode {
    private Type type;

    public ASTNode() {
        this.type = null;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        for (int idx = 0; idx < indentDepth; idx++) {
            str.append("  ");
        }
        return str.toString();
    }

    public String getVSR(int indentDepth) {
        return getASTR(indentDepth);
    }

    public String getBOTLPIF() {
        StringBuilder str = new StringBuilder("");
        str.append(getVSR(0));
        str.append(getASTR(0));
        return str.toString();
    }

    public String getTypePrefix() {
        StringBuilder str = new StringBuilder("");
        if (getNodeType() != null) {
            String type = getNodeType().toString();
            type = type.replaceAll("unsigned", "U");
            type = type.replaceAll("signed", "S");
            type = type.replaceAll("bool", "B");
            str.append(" ");
            str.append(type);
            str.append(":");
        }
        return str.toString();
    }

    public abstract Type getNodeType();
    public abstract ASTNode foldConstants();

    @Override
    public String toString() {
        return getASTR(0);
    }
}
