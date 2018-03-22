package Parser.Nodes;

import Errors.SyntaxError;
import Tokenizer.TokenReader;
import Compiler.CompilerState;
import Compiler.SymbolTable;
import Tokenizer.Tokens.EOFToken;

import java.util.Vector;


public class Program extends ASTNode {
    private Vector<ASTNode> blocks;

    public Program() {
        blocks = new Vector<>();
    }

    public void addBlock(ASTNode block) {
        blocks.add(block);
    }

    public Vector<ASTNode> getBlocks() {
        return blocks;
    }

    @Override
    public String getVSR() {
        StringBuilder str = new StringBuilder("");
        for (ASTNode block : blocks) {
            str.append(block.getVSR());
        }
        return str.toString();
    }

    @Override
    public String getASTR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        for (ASTNode block : blocks) {
            str.append(block.getASTR(0));
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (ASTNode block : blocks) {
            str.append(block);
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Function to be called to parse a program. This will parse multiple statements.
     *
     * @return true if the parse was successful, false otherwise
     */
    public static ASTNode parse(CompilerState cs, SymbolTable st) {
        TokenReader tr = cs.getTr();
        Program program = new Program();
        program.addBlock(Block.parse(cs, st));

        if (EOFToken.isToken(tr.peek())) {
            tr.read();
        }
        else {
            cs.addError(new SyntaxError(tr.read(), "EOF"));
        }
        return program;
    }
}
