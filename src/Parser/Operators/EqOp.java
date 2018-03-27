package Parser.Operators;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;
import Types.PrimType;
import Types.Type;
import Types.TypeEnum;

public class EqOp extends Operator {
    public EqOp(Token token) {
        super(token);
    }

    public Type getNodeType() {
        if (getType() == null) {
            Type lhs = getLhs().getNodeType();
            Type rhs = getRhs().getNodeType();
            if (lhs != null && rhs != null) {
                if (lhs.equals(rhs) || (lhs.getTypeEnum() == TypeEnum.SIGNED && rhs.getTypeEnum() == TypeEnum.UNSIGNED) || (lhs.getTypeEnum() == TypeEnum.UNSIGNED && rhs.getTypeEnum() == TypeEnum.SIGNED)) {
                    setType(new PrimType(TypeEnum.BOOL));
                } else {
                    //exception
                }
            }
        }
        return getType();
    }

    @Override
    public ASTNode foldConstants() {
        setLhs(getLhs().foldConstants());
        setRhs(getRhs().foldConstants());
        //apply op
        return this;
    }

    public static boolean isOp(Token token) {
        return EqOp.isOp(token.getValue());
    }

    public static boolean isOp(String op) {
        switch (op) {
            case "==":
            case "!=":
                return true;
            default:
                return false;
        }
    }
}
