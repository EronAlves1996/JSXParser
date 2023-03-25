package com.eronalves1996;

import java.util.*;
import java.util.stream.Collectors;

public abstract class JSXToken {

    public static JSXElement tokenize(String code) {
        String[] splittedTags = code.split("<");
        Optional<JSXSelfClosingElement> collect = Arrays.stream(splittedTags)
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(s -> s.endsWith("/>") ? new JSXSelfClosingElement(s) : null)
                .filter(Objects::nonNull)
                .findFirst();

        return new JSXElement(collect.get());
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

            String sanitizedElementInfo = s.replace("/>", "");
            String[] unparsedSubTokens = sanitizedElementInfo.split(" ");
            JSXElementName name = new JSXElementName(unparsedSubTokens[0]);


            subTokens.add(name);

            if(unparsedSubTokens.length > 1){
                JSXAttributes attributes = new JSXAttributes(Arrays.asList(unparsedSubTokens).subList(1, unparsedSubTokens.length));
                subTokens.add(attributes);
            }
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
