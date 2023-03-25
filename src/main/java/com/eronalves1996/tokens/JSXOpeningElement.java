package com.eronalves1996.processors;

import java.util.ArrayList;
import java.util.List;

public class JSXOpeningElement extends JSXToken {

    private List<JSXToken> subtokens;

    public JSXOpeningElement(String s) {
        super();
        subtokens = new ArrayList<>();
        String sanitizedName = s.replace(">", "").trim();
        JSXElementName jsxElementName = new JSXElementName(sanitizedName);

        subtokens.add(jsxElementName);
    }
}
