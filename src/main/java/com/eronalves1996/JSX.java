package com.eronalves1996;

public class JSX {

    public static JSX parse(String code){
        return new JSX(code);
    }

    private JSX(String code){}


    public JSXTokenIterator tokens() {
        return new JSXTokenIterator();
    }
}
