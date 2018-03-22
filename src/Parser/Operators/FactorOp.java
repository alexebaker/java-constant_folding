package Parser.Operators;

import Tokenizer.Tokens.Token;

public class FactorOp extends Operator {
    public FactorOp(Token token) {
        super(token);
    }

    public static boolean isOp(Token token) {
        return FactorOp.isOp(token.getValue());
    }

    public static boolean isOp(String op) {
        switch (op) {
            case "*":
            case "/":
                return true;
            default:
                return false;
        }
    }
}
