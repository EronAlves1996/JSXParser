package com.eronalves1996.util;

import java.util.Optional;

public class Either<T, V> {

    public final Optional<T> left;
    public final Optional<V> right;

    public Either(T t, V v){
        this.left = Optional.ofNullable(t);
        this.right = Optional.ofNullable(v);
    }
}
