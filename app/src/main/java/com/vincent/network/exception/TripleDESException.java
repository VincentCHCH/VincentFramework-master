package com.vincent.network.exception;

/**
 * Created by h234385 on 17/8/2017.
 */

public class TripleDESException extends Exception {
    private String errorMessage ;

    public TripleDESException (String msg) {
        errorMessage = msg;
    }
}
