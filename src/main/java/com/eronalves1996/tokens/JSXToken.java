package com.eronalves1996.tokens;

import java.util.*;
import java.util.stream.Collectors;

public abstract class JSXToken {

    protected static Stack<String> validator;

    static {
        validator = new Stack<>();
    }

    public static JSXElement tokenize(String code) {
        String[] tags = identifyTags(code);
        List<JSXToken> jsxTree = mountJSXTokenTree(tags);
        return new JSXElement(jsxTree);
    }

    private static List<JSXToken> mountJSXTokenTree(String[] tags) {
        return Arrays.stream(tags)
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(s -> {
                    if(!s.startsWith("/"))
                        return s.endsWith("/>") ? new JSXSelfClosingElement(s) : new JSXOpeningElement(s);
                    else
                        return new JSXClosingElement(s);

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static String[] identifyTags(String code) {
        return code.split("<");
    }


    public List<JSXToken> subTokens(){
        return new ArrayList<>();
    }





}
