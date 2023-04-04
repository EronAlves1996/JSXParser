package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSXSelfClosingElement extends JSXToken {
    private List<JSXToken> subTokens;

    public JSXSelfClosingElement(String s){
        subTokens = new ArrayList<>();


        String[] nameAndAttributes = JSXToken.sliceStringIn(s, s.indexOf(" "));

        subTokens.add(new JSXElementName(nameAndAttributes[0]));

        if(nameAndAttributes.length > 1) {
            subTokens.add(new JSXAttributes(nameAndAttributes[1].trim()));
        }
    }

    @Override
    public List<JSXToken> subTokens() {
        return subTokens;
    }

    @Override
    public String toString() {
        return "JSXSelfClosingElement{" +
                "subTokens=" + subTokens +
                '}';
    }


}