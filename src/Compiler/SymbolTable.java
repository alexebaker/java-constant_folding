package Compiler;

import Parser.Nodes.ASTNode;
import Tokenizer.Tokens.Token;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;


public class SymbolTable {
    private HashMap<Token, VDI> symbolTable;
    private boolean inDef;
    private SymbolTable parent;

    public SymbolTable() {
        this(null);
    }

    public SymbolTable(SymbolTable parent) {
        symbolTable = new HashMap<>();
        inDef = false;
        this.parent = parent;
    }

    public boolean isInDef() {
        return inDef;
    }

    public void setInDef(boolean inDef) {
        this.inDef = inDef;
    }

    public boolean addDeclaration(Token name, ASTNode type) {
        if (!symbolTable.containsKey(name)) {
            symbolTable.put(name, new VDI(name, "unused", type));
            return true;
        }
        return false;
    }

    public void removeDeclaration(Token name) {
        if (symbolTable.containsKey(name)) {
            symbolTable.remove(name);
        }
        else if (parent != null) {
            parent.removeDeclaration(name);
        }
    }

    public boolean alreadyDeclared(Token name) {
        return symbolTable.containsKey(name) || (parent != null && parent.alreadyDeclared(name));
    }

    public void setUsed(Token name) {
        if (!inDef) {
            VDI vdi;
            if (symbolTable.containsKey(name)) {
                vdi = symbolTable.get(name);
                if (vdi.getType() != null) {
                    vdi.setStatus("okay");
                }
            }
            else if (parent != null && parent.alreadyDeclared(name)) {
                parent.setUsed(name);
            }
            else {
                addDeclaration(name, null);
                vdi = symbolTable.get(name);
                vdi.setStatus("undeclared");
            }
        }
    }

    public String getVSR(int indentDepth) {
        StringBuilder str = new StringBuilder("");
        StringBuilder indentStr = new StringBuilder("");
        for (int idx = 0; idx < indentDepth; idx++) {
            indentStr.append("  ");
        }
        for (Token name : new TreeSet<>(symbolTable.keySet())) {
            str.append(indentStr);
            str.append(symbolTable.get(name));
            str.append("\n");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return getVSR(0);
    }
}
