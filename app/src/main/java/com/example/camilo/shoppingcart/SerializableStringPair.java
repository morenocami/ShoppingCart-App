package com.example.camilo.shoppingcart;

import android.support.v4.util.Pair;

import java.io.Serializable;

/**
 * Created by Camilo on 5/1/2016.
 */
public class SerializableStringPair<String, Integer> extends Pair<String, Integer> implements Serializable {

    private static final long serialVersionUID = 315684651;

    public SerializableStringPair(String first, Integer second) {
        super(first, second);
    }
}