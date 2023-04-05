package com.eronalves1996.tokens;

import java.util.*;
import java.util.stream.Stream;

public class JSXChildren extends JSXToken {
    private List<JSXToken> subTokens;

    {
        subTokens = new ArrayList<>();
    }

    public JSXChildren() {
    }

    public void addToken(JSXToken token) {
        subTokens.add(token);
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
