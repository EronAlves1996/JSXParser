package com.eronalves1996;

import com.eronalves1996.processors.JSXToken;

public class JSX {

    private final JSXToken tokens;

    public static JSX parse(String code){
        return new JSX(code);
    }

    private JSX(String code){
        tokens = JSXToken.tokenize(code);
    }


    public JSXTokenIterator tokens() {
        return tokens.asIterator();
    }

    @Override
    public String toString() {
        return "JSX{" +
                "tokens=" + tokens +
                '}';
    }
}
