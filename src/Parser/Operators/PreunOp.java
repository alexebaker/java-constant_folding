package Parser.Operators;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;
import Types.PointerType;
import Types.PrimType;
import Types.Type;
import Types.TypeEnum;

public class PreunOp extends Operator {
    public PreunOp(Token token) {
        super(token);
    }

    public Type getNodeType() {
        if (getType() == null) {
            Type type = getRhs().getNodeType();
            if (type != null) {
                if (getOp().getValue().equals("--") || getOp().getValue().equals("++")) {
                    if (PointerType.isType(type) || type.getTypeEnum() == TypeEnum.UNSIGNED || type.getTypeEnum() == TypeEnum.SIGNED) {
                        setType(type);
                    }
                } else if (getOp().getValue().equals("-")) {
                    if (type.getTypeEnum() == TypeEnum.UNSIGNED || type.getTypeEnum() == TypeEnum.SIGNED) {
                        setType(new PrimType(type.getTypeEnum()));
                    }
                } else if (getOp().getValue().equals("&")) {
                    PointerType newType = new PointerType();
                    newType.setOfType(type);
                    setType(newType);
                }
            }
        }
        return getType();
    }

    @Override
    public ASTNode foldConstants() {
        setRhs(getRhs().foldConstants());
        return this;
    }

    public static boolean isOp(Token token) {
        return PreunOp.isOp(token.getValue());
    }

    public static boolean isOp(String op) {
        switch (op) {
            case "-":
            case "--":
            case "++":
            case "&":
                return true;
            default:
                return false;
        }
    }
}
