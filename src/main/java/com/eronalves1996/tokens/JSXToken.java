package com.eronalves1996.tokens;

import com.eronalves1996.util.Either;

import java.util.*;

public abstract class JSXToken {

    protected static Stack<String> validator;

    static {
        validator = new Stack<>();
    }

    public static JSXElement tokenize(String code) {
        return new JSXElement(mountJSXTokenTree(code));
    }
    private static List<JSXToken> mountJSXTokenTree(String code){
        ArrayList<JSXToken> tokens = new ArrayList<>();
        code = code.trim();

        List<Either<String, JSXToken>> firstElementAndRestString = parseTopLevelElement(code);
        Either<String, JSXToken> firstToken = firstElementAndRestString.get(0);
        Either<String, JSXToken> restString = firstElementAndRestString.get(1);

        if(firstToken.right.isPresent()) {
            JSXToken jsxToken = firstToken.right.get();
            if (jsxToken instanceof JSXSelfClosingElement) {
                if (restString.right.isPresent())
                    throw new RuntimeException("There should only one self closing tag element on top level");
                tokens.add(jsxToken);
                return tokens;
            }
            if (jsxToken instanceof JSXOpeningElement) {
                tokens.add(jsxToken);
                if (restString.left.isPresent()) {
                    List<JSXToken> jsxTokens = parseRestString(restString.left.get());
                    tokens.addAll(jsxTokens);
                }
            }
        }

        return tokens;
    }


    private static List<Either<String, JSXToken>> parseTopLevelElement(String code){
        if(!code.startsWith("<")) {
            return List.of(new Either(null, new JSXText(code)));
        }

        String[] partition = sliceStringIn(code, code.indexOf(">"));
        Iterator<String> partitionIter = Arrays.stream(partition).iterator();
        String initial = partitionIter.next().trim();
        String end = partitionIter.next().trim();

        String elementName = initial.replace("<", "");

        return List.of(new Either(null, defineTag(elementName)), new Either(end, null));
    }



    private static List<JSXToken> parseRestString(String restString){
        if(!restString.endsWith(">")){
            throw new RuntimeException("There's no closing tag");
        }

        String[] strings = sliceStringIn(restString, restString.lastIndexOf("<"));
        Iterator<String> iterator = Arrays.stream(strings).iterator();
        String childString = iterator.next().substring(1).trim();
        String tagName = iterator.next().trim().replace(">", "").replace("<", "");


        if(childString.isEmpty()) return List.of(defineTag(tagName));
        JSXChildren jsxChild = new JSXChildren(childString);
        return List.of(jsxChild, defineTag(tagName));
    }

    protected static JSXToken defineTag(String tagIdentifier) {
        if(tagIdentifier.endsWith("/")) return new JSXSelfClosingElement(tagIdentifier.substring(0, tagIdentifier.length() - 2));
        if(tagIdentifier.startsWith("/")) return new JSXClosingElement(tagIdentifier.substring(1));
        return new JSXOpeningElement(tagIdentifier);
    }

    protected static String[] sliceStringIn(String snippet, int index){
        if(index == -1){
            return new String[] {snippet};
        }
        String left = snippet.substring(0, index);
        String right = snippet.substring(index);
        return new String[] {left, right};
    }

    public List<JSXToken> subTokens(){
        return new ArrayList<>();
    }

}
