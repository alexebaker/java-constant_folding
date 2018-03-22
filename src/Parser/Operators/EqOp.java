package Parser.Operators;

import Tokenizer.Tokens.Token;

public class EqOp extends Operator {
    public EqOp(Token token) {
        super(token);
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
