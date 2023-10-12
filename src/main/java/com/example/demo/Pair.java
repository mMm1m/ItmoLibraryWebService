package com.example.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<T1, T2> {
    private T1 first = null;
    private T2 second = null;
    Pair() {}
    Pair(T1 f, T2 s )
    {
        this.first = f;
        this.second = s;
    }
}
