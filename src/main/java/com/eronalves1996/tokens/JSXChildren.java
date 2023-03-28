package com.eronalves1996.tokens;

import java.util.ArrayList;
import java.util.List;

public class JSXChildren extends JSXToken {
    private List<JSXToken> subTokens;

    public JSXChildren(String child){
        super();
        subTokens = new ArrayList<>();
        subTokens.add(new JSXText(child));
    }

    @Override
    public List<JSXToken> subTokens(){
        return subTokens;
    }

}
