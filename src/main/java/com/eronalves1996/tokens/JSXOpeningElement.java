package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.List;

public class JSXOpeningElement extends JSXToken {

    private List<JSXToken> subtokens;

    public JSXOpeningElement(String s) {
        super();
        subtokens = new ArrayList<>();

        this.validator.add(s);

        JSXElementName jsxElementName = new JSXElementName(s);
        subtokens.add(jsxElementName);
    }

    @Override
    public List<JSXToken> subTokens(){
        return subtokens;
    }

}
