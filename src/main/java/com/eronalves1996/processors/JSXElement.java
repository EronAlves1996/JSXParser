package com.eronalves1996.processors;

public class JSXElement extends JSXToken {
    JSXToken tokens;

    public JSXElement(JSXToken tokens){
        this.tokens = tokens;
    }

    @Override
    public String toString() {
        return "JSXElement{\n" +
                "\ttokens=" + tokens +
                "\n}";
    }
}