package com.eronalves1996;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class JSXTokenIterator implements Iterator<JSXToken> {


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public JSXToken next() {
        return new JSXToken.JSXSelfClosingElement();
    }

    @Override
    public void remove() {
        Iterator.super.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super JSXToken> action) {
        Iterator.super.forEachRemaining(action);
    }
}
