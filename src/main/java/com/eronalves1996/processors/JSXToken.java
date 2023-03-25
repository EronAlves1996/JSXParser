package com.eronalves1996.processors;

import com.eronalves1996.JSXTokenIterator;

import java.util.*;

public abstract class JSXToken {

    public static JSXElement tokenize(String code) {
        String[] tags = identifyTags(code);
        Optional<JSXSelfClosingElement> jsxTree = mountJSXTokenTree(tags);
        return new JSXElement(jsxTree.get());
    }

    private static Optional<JSXSelfClosingElement> mountJSXTokenTree(String[] tags) {
        return Arrays.stream(tags)
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(s -> s.endsWith("/>") ? new JSXSelfClosingElement(s) : null)
                .filter(Objects::nonNull)
                .findFirst();
    }

    private static String[] identifyTags(String code) {
        return code.split("<");
    }

    public JSXTokenIterator asIterator() {
        if (this instanceof JSXElement){
            return new JSXTokenIterator(((JSXElement) this).tokens);
        }
        return null;
    }

    public List<JSXToken> subTokens(){
        return new ArrayList<>();
    }





}
