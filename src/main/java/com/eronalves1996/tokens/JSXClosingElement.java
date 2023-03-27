package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.List;

public class JSXClosingElement extends JSXToken {

    private List<JSXToken> subTokens;


    public JSXClosingElement(String s) {
        super();
        subTokens = new ArrayList<>();

        String sanitizedElementName = s.replace(">", "").replace("/","");

        String pop = this.validator.pop();
        System.out.println("pop = " +  pop);
        System.out.println("sanitized name = " + sanitizedElementName);
        if(!sanitizedElementName.equals(pop)) throw new RuntimeException("This tag was not opened in JSXTree");

        subTokens.add(new JSXElementName(sanitizedElementName));
    }

}
