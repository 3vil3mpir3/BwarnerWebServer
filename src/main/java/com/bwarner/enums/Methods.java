package com.bwarner.enums;

/** Borrowed this from elsewhere, removed types I won't be supporting within the context of this exercise **/

public enum Methods {
    GET("GET"), //
    HEAD("HEAD"), //
    TRACE("TRACE"), //
    UNRECOGNIZED(null); //

    private final String method;

    Methods(String method) {
        this.method = method;
    }

}
