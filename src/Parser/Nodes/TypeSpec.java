package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Types.Type;

import java.util.Vector;

public class TypeSpec extends ASTNode {
    private ASTNode typeName;
    private Vector<ASTNode> arraySpecs;

    public TypeSpec() {
        typeName = null;
        arraySpecs = new Vector<>();
    }

    public void addArraySpec(ASTNode arraySpec) {
        arraySpecs.add(arraySpec);
    }

    public Vector<ASTNode> getArraySpecs() {
        return arraySpecs;
    }

    public void setTypeName(ASTNode typeName) {
        this.typeName = typeName;
    }

    public ASTNode getTypeName() {
        return typeName;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        str.append(typeName.getASTR(indentDepth));
        for (ASTNode arraySpec : arraySpecs) {
            str.append(arraySpec.getASTR(indentDepth));
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        TypeSpec typeSpec = new TypeSpec();
        typeSpec.setTypeName(PrimType.parse(cs, st));

        while (tr.peek().getValue().equals("[")) {
            typeSpec.addArraySpec(ArraySpec.parse(cs, st));
        }
        return typeSpec;
    }

    public Type getNodeType() {
        if (getType() == null) {
            Type type = typeName.getNodeType();
            Type tmp;
            for (ASTNode arraySpec : arraySpecs) {
                tmp = arraySpec.getNodeType();
                tmp.setOfType(type);
                type = tmp;
            }
            setType(type);
        }
        return getType();
    }

}
