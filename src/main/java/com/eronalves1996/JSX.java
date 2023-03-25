package com.eronalves1996;

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
<<<<<<< Updated upstream
=======

    @Override
    public String toString() {
        return "JSX{\n" +
                "\ttokens=" + tokens +
                "\n}";
    }
>>>>>>> Stashed changes
}
