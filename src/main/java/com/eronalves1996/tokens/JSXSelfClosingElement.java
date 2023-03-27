package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSXSelfClosingElement extends JSXToken {
    private List<JSXToken> subTokens;

    public JSXSelfClosingElement(String s){
        subTokens = new ArrayList<>();

        String sanitizedElementInfo = s.replace("/>", "");
        String[] unparsedSubTokens = sanitizedElementInfo.split(" ");

        parseElementName(unparsedSubTokens);
        parseAttributes(unparsedSubTokens);
    }

    private void parseElementName(String[] unparsedSubTokens) {
        JSXElementName name = new JSXElementName(unparsedSubTokens[0]);
        subTokens.add(name);
    }

    private void parseAttributes(String[] unparsedSubTokens) {
        if(unparsedSubTokens.length > 1){
            JSXAttributes attributes = new JSXAttributes(Arrays.asList(unparsedSubTokens).subList(1, unparsedSubTokens.length));
            subTokens.add(attributes);
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