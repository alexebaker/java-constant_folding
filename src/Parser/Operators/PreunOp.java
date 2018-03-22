package Parser.Operators;

import Tokenizer.Tokens.Token;

public class PreunOp extends Operator {
    public PreunOp(Token token) {
        super(token);
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
