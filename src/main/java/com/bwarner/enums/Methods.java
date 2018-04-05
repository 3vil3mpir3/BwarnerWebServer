package com.bwarner.enums;

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
