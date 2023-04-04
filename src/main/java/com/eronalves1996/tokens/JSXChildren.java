package com.eronalves1996.tokens;

import com.eronalves1996.util.Either;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JSXChildren extends JSXToken {
    private List<JSXToken> subTokens;

    public JSXChildren(String child){
        super();
        subTokens = new ArrayList<>();
        parse(child);
    }

    private void parse(String child){
        String[] split = child.split(">");
        if(split.length > 1){
            Stream<List<JSXToken>> listStream = Arrays.stream(split).map(token -> {
                if (token.startsWith("<")) {
                    String substring = token.substring(1);
                    return List.of(JSXToken.defineTag(substring));
                }

                if (token.contains("<")) {
                    String[] split1 = token.split("<");
                    List<JSXToken> tokens = new ArrayList<>();
                    tokens.add(new JSXText(split1[0].trim()));
                    tokens.add(JSXToken.defineTag(split1[1].trim()));
                    return tokens;
                }

                if (token.isEmpty()) return List.of(null);

                return List.of(new JSXText(token));
            });
            List<JSXToken> collect = listStream.flatMap(item -> item.stream()).filter(Objects::nonNull).collect(Collectors.toList());
        }
        else subTokens.add(new JSXText(child));
    }

    @Override
    public List<JSXToken> subTokens(){
        return Collections.unmodifiableList(subTokens);
    }

    @Override
    public String toString() {
        return "JSXChildren{" +
                "subTokens=" + subTokens +
                '}';
    }
}
