package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.List;

public class JSXClosingElement extends JSXToken {

    private List<JSXToken> subTokens;


    public JSXClosingElement(String s) {
        super();
        subTokens = new ArrayList<>();

        String pop = this.validator.pop();

        if(!s.equals(pop)) throw new RuntimeException("This tag was not opened in JSXTree");

        subTokens.add(new JSXElementName(s));
    }

    @Override
    public List<JSXToken> subTokens(){
        return subTokens;
    }
}
