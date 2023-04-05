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
            List<JSXToken> flatten = flatten(collect);
            System.out.println(flatten);
        } else subTokens.add(new JSXText(child));
    }

    private List<JSXToken> flatten(List<JSXToken> unflattenedTokens){

        Iterator<JSXToken> tokenIterator = unflattenedTokens.iterator();
        List<JSXToken> flattenedList = new ArrayList<>();
        String identifierName = "";

        while(tokenIterator.hasNext()){
            JSXToken nextToken = tokenIterator.next();
            if(List.of(JSXOpeningElement.class, JSXClosingElement.class, JSXSelfClosingElement.class).contains(nextToken.getClass())){
                flattenedList.add(nextToken);
                if(nextToken.getClass() == JSXOpeningElement.class){
                    identifierName = ((JSXOpeningElement) nextToken).getIdentifier();
                }
            }
            else {
                JSXChildren children = new JSXChildren();
                flattenedList.add(children);
                children.addToken(nextToken);
                JSXToken closingTag = flatten(tokenIterator, children, identifierName);
                flattenedList.add(closingTag);
            }
        }
        return flattenedList;
    }

    private JSXToken flatten(Iterator<JSXToken> tokenIterator, JSXChildren children, String referenceIdentifier){

        while(tokenIterator.hasNext()){
            JSXToken nextToken = tokenIterator.next();
            if(JSXOpeningElement.class == nextToken.getClass()){
                children.addToken(nextToken);
                String newReferenceIdentifier = ((JSXOpeningElement) nextToken).getIdentifier();
                JSXChildren subChildren = new JSXChildren();
                children.addToken(subChildren);
                JSXToken closingTag = flatten(tokenIterator, subChildren, newReferenceIdentifier);
                children.addToken(closingTag);
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
