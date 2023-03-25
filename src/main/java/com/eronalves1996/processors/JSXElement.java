package com.eronalves1996.processors;

public class JSXElement extends JSXToken {
    public JSXToken topLevelToken;

    public JSXElement(JSXToken tokens){
        this.topLevelToken = tokens;
    }

    @Override
    public String toString() {
        return "JSXElement{" +
                "tokens=" + topLevelToken +
                '}';
    }

}