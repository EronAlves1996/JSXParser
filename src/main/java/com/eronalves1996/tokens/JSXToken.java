package com.eronalves1996.tokens;

import java.util.*;
import java.util.stream.Stream;

public abstract class JSXToken {

    protected static Stack<String> validator;

    static {
        validator = new Stack<>();
    }

    public static JSXElement tokenize(String code) {
        return mountJSXTokenTree(code);
    }

    private static JSXElement mountJSXTokenTree(String code) {
        String[] split = code.split(">");
        if (split.length > 1) {
            Stream<List<JSXToken>> tokenStream = Arrays
                    .stream(split)
                    .map(JSXToken::generateListOfTokens);

            JSXToken jsxElement = Optional.of(
                            tokenStream
                                    .flatMap(Collection::stream)
                                    .filter(Objects::nonNull)
                                    .toList()
                    )
                    .map(JSXToken::groupInChildrens)
                    .get()
                    .get(0);

            return (JSXElement) jsxElement;
        } else return new JSXElement(List.of(new JSXText(code)));
    }

    private static List<JSXToken> generateListOfTokens(String token) {
        if (token.startsWith("<")) {
            String substring = token.substring(1).trim();
            if (substring.isEmpty()) return null;
            return List.of(JSXToken.defineTag(substring));
        }

        if (token.contains("<")) {
            String[] split1 = token.split("<");
            List<JSXToken> tokens = new ArrayList<>();
            String text = split1[0].trim();

            if (text.isEmpty()) tokens.add(null);
            else tokens.add(new JSXText(text));

            tokens.add(JSXToken.defineTag(split1[1].trim()));
            return tokens;
        }

        if (token.isEmpty()) return List.of(null);

        return List.of(new JSXText(token));
    }

    private static List<JSXToken> groupInChildrens(List<JSXToken> allTokens) {

        Iterator<JSXToken> tokenIterator = allTokens.iterator();
        List<JSXToken> flattenedList = new ArrayList<>();

        while (tokenIterator.hasNext()) {
            JSXToken nextToken = tokenIterator.next();

            if (JSXOpeningElement.class == nextToken.getClass()) {
                JSXChildren children = new JSXChildren();
                List<JSXToken> elementTokens = new ArrayList<>();
                elementTokens.addAll(List.of(nextToken, children));

                String identifierName = ((JSXOpeningElement) nextToken).getIdentifier();
                JSXToken jsxToken = groupInChildrens(tokenIterator, children, identifierName);

                elementTokens.add(jsxToken);
                flattenedList.add(new JSXElement(elementTokens));
            } else if (nextToken.getClass() == JSXSelfClosingElement.class) {
                flattenedList.add(new JSXElement(List.of(nextToken)));
            }
        }
        return flattenedList;
    }

    private static JSXToken groupInChildrens(Iterator<JSXToken> tokenIterator, JSXChildren children, String referenceIdentifier) {

        while (tokenIterator.hasNext()) {
            JSXToken nextToken = tokenIterator.next();
            if (JSXOpeningElement.class == nextToken.getClass()) {
                List<JSXToken> elementList = new ArrayList<>();
                elementList.add(nextToken);
                String newReferenceIdentifier = ((JSXOpeningElement) nextToken).getIdentifier();
                JSXChildren subChildren = new JSXChildren();
                elementList.add(subChildren);
                JSXToken closingTag = groupInChildrens(tokenIterator, subChildren, newReferenceIdentifier);
                elementList.add(closingTag);
                children.addToken(new JSXElement(elementList));
            } else if (nextToken.getClass() == JSXClosingElement.class) {
                String identifier = ((JSXClosingElement) nextToken).getIdentifier();
                if (identifier.equals(referenceIdentifier)) {
                    if (children.subTokens().isEmpty()) {
                        throw new RuntimeException("You should use a self closing tag on <" + identifier + ">");
                    }
                    return nextToken;
                }
            } else {
                children.addToken(nextToken);
            }
        }

        return null;
    }

    protected static JSXToken defineTag(String tagIdentifier) {
        if (tagIdentifier.endsWith("/"))
            return new JSXSelfClosingElement(tagIdentifier.substring(0, tagIdentifier.length() - 2));
        if (tagIdentifier.startsWith("/")) return new JSXClosingElement(tagIdentifier.substring(1));
        return new JSXOpeningElement(tagIdentifier);
    }

    protected static String[] sliceStringIn(String snippet, int index) {
        if (index == -1) {
            return new String[]{snippet};
        }
        String left = snippet.substring(0, index);
        String right = snippet.substring(index);
        return new String[]{left, right};
    }

    public List<JSXToken> subTokens() {
        return new ArrayList<>();
    }

}
