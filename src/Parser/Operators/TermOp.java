package Parser.Operators;

import Tokenizer.Tokens.Token;

public class TermOp extends Operator {
    public TermOp(Token token) {
        super(token);
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
