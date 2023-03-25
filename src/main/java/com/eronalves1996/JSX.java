package com.eronalves1996;

import com.eronalves1996.processors.JSXElement;
import com.eronalves1996.processors.JSXToken;

public class JSX {

    public final JSXElement tokens;

    public static JSX parse(String code){
        return new JSX(code);
    }

    private JSX(String code){
        tokens = JSXToken.tokenize(code);
    }

    @Override
    public String toString() {
        return "JSX{" +
                "tokens=" + tokens +
                '}';
    }
}
