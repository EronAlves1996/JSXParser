package com.eronalves1996;

import java.util.*;
import java.util.stream.Collectors;

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

    public static class JSXElement extends JSXToken {
        private JSXToken tokens;

        public JSXElement(JSXToken tokens){
            this.tokens = tokens;
        }

    }


    public static class JSXSelfClosingElement extends JSXToken {
        private List<JSXToken> subTokens;

        public JSXSelfClosingElement(String s){
            subTokens = new ArrayList<>();

            String sanitizedElementInfo = sanitizeElementInfo(s);
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

        private static String sanitizeElementInfo(String s) {
            return s.replace("/>", "");
        }

        @Override
        public List<JSXToken> subTokens() {
            return subTokens;
        }
    }

    public static class JSXElementName extends JSXToken {
        public String identifier;

        public JSXElementName(String identifier){
            this.identifier = identifier;
        }
    }

    public static class JSXAttributes extends JSXToken {

        private Map<String, String> attributes;

        public JSXAttributes(List<String> unparsedAttributes) {
            super();
            attributes = new HashMap<>();
            parseAttributes(unparsedAttributes);
        }

        private void parseAttributes(List<String> unparsedAttributes) {
            unparsedAttributes.stream().forEach(attr -> {
                String[] nameAndValue = attr.split("=");
                attributes.put(nameAndValue[0], nameAndValue[1].replaceAll("\"", ""));
            });
        }

        public boolean has(String type) {
            return attributes.containsKey(type);
        }

        public String get(String type) {
            return attributes.get(type);
        }


    }

}
