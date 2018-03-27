package Parser.Nodes;

import Compiler.CompilerState;
import Compiler.SymbolTable;
import Errors.SyntaxError;
import Parser.Operators.PreunOp;
import Tokenizer.TokenReader;
import Tokenizer.Tokens.Token;
import Types.Type;


public class Statement extends ASTNode {
    private ASTNode expr;
    private ASTNode block;
    private ASTNode ifStmt;
    private ASTNode whileStmt;

    public Statement() {
        super();
        expr = null;
        block = null;
        ifStmt = null;
        whileStmt = null;
    }

    public void setExpr(ASTNode expr) {
        this.expr = expr;
    }

    public ASTNode getExpr() {
        return expr;
    }

    public void setBlock(ASTNode block) {
        this.block = block;
    }

    public ASTNode getBlock() {
        return block;
    }

    public void setIfStmt(ASTNode ifStmt) {
        this.ifStmt = ifStmt;
    }

    public ASTNode getIfStmt() {
        return ifStmt;
    }

    public void setWhileStmt(ASTNode whileStmt) {
        this.whileStmt = whileStmt;
    }

    public ASTNode getWhileStmt() {
        return whileStmt;
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        String indentStr = super.getASTR(indentDepth);
        if (expr != null) {
            str.append(indentStr);
            str.append(expr.getASTR(indentDepth));
            str.append(";\n");
        }
        else if (ifStmt != null) {
            str.append(indentStr);
            str.append(ifStmt.getASTR(indentDepth));
            str.append("\n");
        }
        else if (whileStmt != null) {
            str.append(indentStr);
            str.append(whileStmt.getASTR(indentDepth));
            str.append("\n");
        }
        else {
            str.append(indentStr);
            str.append("{\n");
            if (block != null) {
                str.append(block.getVSR(indentDepth+1));
                str.append(block.getASTR(indentDepth+1));
            }
            str.append(indentStr);
            str.append("}\n");
        }
        return str.toString();
    }

    public static ASTNode parse(CompilerState cs, SymbolTable st) throws SyntaxError {
        TokenReader tr = cs.getTr();
        Statement stmt = new Statement();
        if (tr.peek().getValue().equals("{")) {
            tr.read();

            stmt.setBlock(Block.parse(cs, st));

            if (tr.peek().getValue().equals("}")) {
                tr.read();
            }
            else {
                throw new SyntaxError(tr.read(), "}");
            }
        }
        else if (tr.peek().getValue().equals("if")) {
            stmt.setIfStmt(IfStmt.parse(cs, st));
        }
        else if (tr.peek().getValue().equals("while")) {
            stmt.setWhileStmt(WhileStmt.parse(cs, st));
        }
        else {
            try {
                stmt.setExpr(Expr.parse(cs, st));
                Token nextToken = tr.peek();
                if (nextToken.getValue().equals(";")) {
                    tr.read();
                }
                else {
                    if (!nextToken.getValue().equals("}")) {
                        nextToken = tr.read();
                    }
                    throw new SyntaxError(nextToken, ";");
                }
            }
            catch (SyntaxError ex) {
                cs.addError(ex);
                Token recoveredToken = tr.recoverFromError();
                if (!recoveredToken.getValue().equals(";")) {
                    throw new SyntaxError(recoveredToken, ";");
                }
            }
        }
        return stmt;
    }

    public static boolean beginsStmt(Token token) {
        return Statement.beginsStmt(token.getValue());
    }

    public static boolean beginsStmt(String str) {
        return PrimaryExpr.beginsPrimaryExpr(str) || str.equals("{") || PreunOp.isOp(str) || str.equals("if") || str.equals("while");
    }

    public Type getNodeType() {
        if (getType() == null) {
            if (expr != null) {
                setType(expr.getNodeType());
            }
            else if (block != null) {
                setType(block.getNodeType());
            }
            else if (ifStmt != null) {
                setType(ifStmt.getNodeType());
            }
            else if (whileStmt != null) {
                setType(whileStmt.getNodeType());
            }
        }
        return getType();
    }

}
