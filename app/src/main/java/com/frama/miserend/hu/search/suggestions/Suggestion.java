package com.frama.miserend.hu.search.suggestions;

import java.io.Serializable;

/**
 * Created by maczak on 2018. 02. 15..
 */

public abstract class Suggestion<T> implements Serializable {
    private T data;

    public Suggestion(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
