package com.eronalves1996.tokens;

import java.util.List;

public class JSXElement extends JSXToken {
    public List<JSXToken> topLevelTokens;

    public JSXElement(List<JSXToken> tokens){
        if(tokens.size() == 2) throw new RuntimeException("You should use a self-closing tag");
        this.topLevelTokens = tokens;
    }

    @Override
    public String toString() {
        return "JSXElement{" +
                "tokens=" + topLevelTokens +
                '}';
    }

}