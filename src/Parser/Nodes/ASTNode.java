package Parser.Nodes;


public abstract class ASTNode {
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

    public String getBOFPIF() {
        StringBuilder str = new StringBuilder("");
        str.append(getVSR(0));
        str.append(getASTR(0));
        return str.toString();
    }

    @Override
    public String toString() {
        return getASTR(0);
    }
}
