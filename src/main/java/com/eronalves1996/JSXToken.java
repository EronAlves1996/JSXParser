package com.eronalves1996;

import java.util.ArrayList;
import java.util.List;

public abstract class JSXToken {

    public List<JSXToken> subTokens() {
        List<JSXToken> subTokenList = new ArrayList<>();
        subTokenList.add(new JSXToken.JSXElementName("input"));
        return subTokenList;
    }

    public static class JSXSelfClosingElement extends JSXToken {}

    public static class JSXElementName extends JSXToken {
        public String identifier;

        public JSXElementName(String identifier){
            this.identifier = identifier;
        }
    }

}
