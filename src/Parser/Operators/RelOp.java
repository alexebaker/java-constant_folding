package Parser.Operators;

import Tokenizer.Tokens.Token;

public class RelOp extends Operator {
    public RelOp(Token token) {
        super(token);
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
