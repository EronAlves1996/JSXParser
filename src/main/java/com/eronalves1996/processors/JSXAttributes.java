package com.eronalves1996.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSXAttributes extends JSXToken {

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

    @Override
    public String toString() {
        return "JSXAttributes{\n" +
                "\tattributes=" + attributes +
                "\n}";
    }
}