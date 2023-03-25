package com.eronalves1996.processors;

import java.util.*;

public abstract class JSXToken {

    public static JSXElement tokenize(String code) {
        String[] tags = identifyTags(code);
        Optional<JSXToken> jsxTree = mountJSXTokenTree(tags);
        return new JSXElement(jsxTree.get());
    }

    private static Optional<JSXToken> mountJSXTokenTree(String[] tags) {
        return Arrays.stream(tags)
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(s -> s.endsWith("/>") ? new JSXSelfClosingElement(s) : new JSXOpeningElement(s))
                .filter(Objects::nonNull)
                .findFirst();
    }

    private static String[] identifyTags(String code) {
        return code.split("<");
    }
    

    public List<JSXToken> subTokens(){
        return new ArrayList<>();
    }





}
