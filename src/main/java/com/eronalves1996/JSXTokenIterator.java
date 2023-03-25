package com.eronalves1996;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class JSXTokenIterator implements Iterator<JSXToken> {

    private Iterator<JSXToken> iterator;

    public JSXTokenIterator(JSXToken tokens) {
        iterator = Arrays.asList(tokens).iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public JSXToken next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super JSXToken> action) {
        iterator.forEachRemaining(action);
    }
<<<<<<< Updated upstream
=======

    @Override
    public String toString() {
        return "JSXTokenIterator{\n" +
                "\titerator=" + iterator +
                "\n}";
    }
>>>>>>> Stashed changes
}
