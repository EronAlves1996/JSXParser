package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JSXOpeningElement extends JSXToken {

    private List<JSXToken> subtokens;

    public JSXOpeningElement(String s) {
        super();
        subtokens = new ArrayList<>();

        String[] nameAndAttributes = JSXToken.sliceStringIn(s, s.indexOf(" "));


        JSXElementName jsxElementName = new JSXElementName(nameAndAttributes[0]);
        this.validator.add(nameAndAttributes[0]);
        subtokens.add(jsxElementName);

        if(nameAndAttributes.length > 1) {
            subtokens.add(new JSXAttributes(nameAndAttributes[1].trim()));
        }


    }

    @Override
    public List<JSXToken> subTokens(){
        return subtokens;
    }

}
