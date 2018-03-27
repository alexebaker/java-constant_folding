package Parser.Operators;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;
import Types.PointerType;
import Types.Type;
import Types.TypeEnum;

public class PostunOp extends Operator {
    public PostunOp(Token token) {
        super(token);
    }

    public Type getNodeType() {
        if (getType() == null) {
            Type type = getLhs().getNodeType();
            if (type != null) {
                if (PointerType.isType(type) || type.getTypeEnum() == TypeEnum.UNSIGNED || type.getTypeEnum() == TypeEnum.SIGNED) {
                    setType(type);
                }
            }
        }
        return getType();
    }

    @Override
    public ASTNode foldConstants() {
        setLhs(getLhs().foldConstants());
        return this;
    }

    public static boolean isOp(Token token) {
        return PostunOp.isOp(token.getValue());
    }

    public static boolean isOp(String op) {
        switch (op) {
            case "--":
            case "++":
                return true;
            default:
                return false;
        }
    }
}
