package com.eronalves1996;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class JSXTokenIterator implements Iterable<JSXToken> {

    @Override
    public Iterator<JSXToken> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super JSXToken> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<JSXToken> spliterator() {
        return Iterable.super.spliterator();
    }
}
