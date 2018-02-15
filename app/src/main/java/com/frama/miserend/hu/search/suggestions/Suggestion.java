package com.frama.miserend.hu.search.suggestions;

/**
 * Created by maczak on 2018. 02. 15..
 */

public abstract class Suggestion<T> {
    private T data;

    public Suggestion(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
