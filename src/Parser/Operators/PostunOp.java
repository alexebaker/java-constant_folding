package Parser.Operators;

import Tokenizer.Tokens.Token;

public class PostunOp extends Operator {
    public PostunOp(Token token) {
        super(token);
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
