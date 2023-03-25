package com.eronalves1996.processors;

public class JSXElement extends JSXToken {
    public JSXToken topLevelTokens;

    public JSXElement(JSXToken tokens){
        this.topLevelTokens = tokens;
    }

    @Override
    public String toString() {
        return "JSXElement{" +
                "tokens=" + topLevelTokens +
                '}';
    }

}