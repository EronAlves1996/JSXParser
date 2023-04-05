package com.eronalves1996.tokens;

import java.util.*;
import java.util.stream.Stream;

public class JSXChildren extends JSXToken {
    private List<JSXToken> subTokens;

    {
        subTokens = new ArrayList<>();
    }

    public JSXChildren(String child) {
        super();
        parse(child);
    }

    public JSXChildren() {
    }

    public void addToken(JSXToken token) {
        subTokens.add(token);
    }

    private void parse(String child) {
        String[] split = child.split(">");
        if (split.length > 1) {
            Stream<List<JSXToken>> listStream = Arrays.stream(split).map(token -> {
                if (token.startsWith("<")) {
                    String substring = token.substring(1).trim();
                    if(substring.isEmpty()) return null;
                    return List.of(JSXToken.defineTag(substring));
                }

                if (token.contains("<")) {
                    String[] split1 = token.split("<");
                    List<JSXToken> tokens = new ArrayList<>();
                    String text = split1[0].trim();

                    if(text.isEmpty()) tokens.add(null);
                    else tokens.add(new JSXText(text));

                    tokens.add(JSXToken.defineTag(split1[1].trim()));
                    return tokens;
                }

                if (token.isEmpty()) return List.of(null);

                return List.of(new JSXText(token));
            });
            List<JSXToken> collect = listStream.flatMap(Collection::stream).filter(Objects::nonNull).toList();

            List<JSXToken> processed = groupInChildrens(collect);
        } else subTokens.add(new JSXText(child));
    }

    private List<JSXToken> groupInChildrens(List<JSXToken> allTokens){

        Iterator<JSXToken> tokenIterator = allTokens.iterator();
        List<JSXToken> flattenedList = new ArrayList<>();

        while(tokenIterator.hasNext()){
            JSXToken nextToken = tokenIterator.next();

            if(JSXOpeningElement.class == nextToken.getClass()){
                JSXChildren children = new JSXChildren();
                List<JSXToken> elementTokens =  new ArrayList<>();
                elementTokens.addAll(List.of(nextToken, children));

                String identifierName = ((JSXOpeningElement) nextToken).getIdentifier();
                JSXToken jsxToken = groupInChildrens(tokenIterator, children, identifierName);

                elementTokens.add(jsxToken);
                flattenedList.add(new JSXElement(elementTokens));
            }
            else if(nextToken.getClass() == JSXSelfClosingElement.class){
                flattenedList.add(new JSXElement(List.of(nextToken)));
            }
        }
        return flattenedList;
    }

    private JSXToken groupInChildrens(Iterator<JSXToken> tokenIterator, JSXChildren children, String referenceIdentifier){

        while(tokenIterator.hasNext()){
            JSXToken nextToken = tokenIterator.next();
            if(JSXOpeningElement.class == nextToken.getClass()){
                List<JSXToken> elementList = new ArrayList<>();
                elementList.add(nextToken);
                String newReferenceIdentifier = ((JSXOpeningElement) nextToken).getIdentifier();
                JSXChildren subChildren = new JSXChildren();
                elementList.add(subChildren);
                JSXToken closingTag = groupInChildrens(tokenIterator, subChildren, newReferenceIdentifier);
                elementList.add(closingTag);
                children.addToken(new JSXElement(elementList));
            }
            else if(nextToken.getClass() == JSXClosingElement.class){
                String identifier = ((JSXClosingElement) nextToken).getIdentifier();
                if(identifier.equals(referenceIdentifier)) return nextToken;
            }
            else {
                children.addToken(nextToken);
            }
        }

        return null;
    }

    @Override
    public List<JSXToken> subTokens() {
        return Collections.unmodifiableList(subTokens);
    }

    @Override
    public String toString() {
        return "JSXChildren{" +
                "subTokens=" + subTokens +
                '}';
    }


}
