package com.eronalves1996.processors;

public class JSXElementName extends JSXToken {
    public String identifier;

    public JSXElementName(String identifier){
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "JSXElementName{\n" +
                "\tidentifier='" + identifier + '\'' +
                "\n}";
    }
}