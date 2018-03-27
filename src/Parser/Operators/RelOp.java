package Parser.Operators;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;
import Types.PointerType;
import Types.PrimType;
import Types.Type;
import Types.TypeEnum;

public class RelOp extends Operator {
    public RelOp(Token token) {
        super(token);
    }

    public Type getNodeType() {
        if (getType() == null) {
            Type lhs = getLhs().getNodeType();
            Type rhs = getRhs().getNodeType();
            if (lhs != null && rhs != null) {
                if (PointerType.isType(lhs) || lhs.getTypeEnum() == TypeEnum.SIGNED || lhs.getTypeEnum() == TypeEnum.UNSIGNED) {
                    if (lhs.equals(rhs)) {
                        setType(new PrimType(TypeEnum.BOOL));
                    }
                }
            }
        }
        return getType();
    }

    @Override
    public ASTNode foldConstants() {
        setLhs(getLhs().foldConstants());
        setRhs(getRhs().foldConstants());
        // apply op
        return this;
    }

    public static boolean isOp(Token token) {
        return RelOp.isOp(token.getValue());
    }

    public static boolean isOp(String op) {
        switch (op) {
            case "<":
            case "<=":
            case ">":
            case ">=":
                return true;
            default:
                return false;
        }
    }
}
