package com.eronalves1996.tokens;

public class JSXText extends JSXToken {

    public final String value;

    public JSXText(String content){
        this.value = content;
    }

    @Override
    public String toString() {
        return "JSXText{" +
                "value='" + value + '\'' +
                '}';
    }
}
