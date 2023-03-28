package com.eronalves1996.tokens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSXAttributes extends JSXToken {

    private Map<String, String> attributes;

    public JSXAttributes(String unparsedAttributes) {
        super();
        attributes = new HashMap<>();
        parseAttributes(unparsedAttributes);
    }

    private void parseAttributes(String unparsedAttributes) {
        String carryString = unparsedAttributes;
        while(!carryString.isEmpty()){
            carryString = parseSingleAttribute(carryString);
        }
    }

    private String parseSingleAttribute(String unparsedString){
        String[] attributeNameAndRest = JSXToken.sliceStringIn(unparsedString, unparsedString.indexOf("="));
        String rest = attributeNameAndRest[1].substring(1);
        String[] attributeValueAndRest = sliceStringIn(rest, rest.substring(1).indexOf("\"") + 1);
        attributes.put(attributeNameAndRest[0].trim(), attributeValueAndRest[0].substring(1).trim());
        return attributeValueAndRest[1].substring(1).trim();
    }

    public boolean has(String type) {
        return attributes.containsKey(type);
    }

    public String get(String type) {
        return attributes.get(type);
    }

    @Override
    public String toString() {
        return "JSXAttributes{" +
                "attributes=" + attributes +
                '}';
    }
}