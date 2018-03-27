package Parser.Operators;

import Tokenizer.Tokens.Token;
import Types.PrimType;
import Types.Type;
import Types.TypeEnum;

public class TermOp extends Operator {
    public TermOp(Token token) {
        super(token);
    }

    public Type getNodeType() {
        if (getType() == null) {
            Type lhs = getLhs().getNodeType();
            Type rhs = getRhs().getNodeType();
            if (lhs != null && rhs != null) {
                if (lhs.getTypeEnum() == TypeEnum.UNSIGNED && (rhs.getTypeEnum() == TypeEnum.UNSIGNED || rhs.getTypeEnum() == TypeEnum.SIGNED)) {
                    setType(new PrimType(TypeEnum.UNSIGNED));
                } else if (lhs.getTypeEnum() == TypeEnum.SIGNED) {
                    if (rhs.getTypeEnum() == TypeEnum.SIGNED) {
                        setType(new PrimType(TypeEnum.SIGNED));
                    } else if (rhs.getTypeEnum() == TypeEnum.UNSIGNED) {
                        setType(new PrimType(TypeEnum.UNSIGNED));
                    } else {
                        //exception
                    }
                } else {
                    //exception
                }
            }
        }
        return getType();
    }

    public static boolean isOp(Token token) {
        return TermOp.isOp(token.getValue());
    }

    public static boolean isOp(String op) {
        switch (op) {
            case "+":
            case "-":
                return true;
            default:
                return false;
        }
    }
}
