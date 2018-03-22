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

    public void addDeclaration(Token name, ASTNode type) {
       symbolTable.put(name, new VDI(name, "unused", type));
    }

    public void removeDeclaration(Token name) {
        symbolTable.remove(name);
    }

    public boolean alreadyDeclared(Token name) {
        return symbolTable.containsKey(name);
    }

    public void setUsed(Token name) {
        if (!inDef) {
            VDI vdi;
            if (symbolTable.containsKey(name)) {
                vdi = symbolTable.get(name);
                if (vdi.getType() != null) {
                    vdi.setStatus("okay");
                }
            } else {
                addDeclaration(name, null);
                vdi = symbolTable.get(name);
                vdi.setStatus("undeclared");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (Token name : new TreeSet<>(symbolTable.keySet())) {
            str.append(symbolTable.get(name));
            str.append("\n");
        }
        return str.toString();
    }
}
