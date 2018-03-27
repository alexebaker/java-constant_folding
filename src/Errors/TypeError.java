package Errors;


public class TypeError extends Error {
    private String msg;

    public TypeError() {
        this("");
    }

    public TypeError(String msg) {
        this.msg = msg;
    }

    @Override
    public String getErrorMsg() {
        StringBuilder str = new StringBuilder("");
        str.append("Type Error! ");
        str.append(msg);
        return str.toString();
    }
}
