package com.vincent.network.exception;

/**
 * Created by h234385 on 17/8/2017.
 */

public class CryptoUtilException extends Exception {

    private String errorMessage ;

    public CryptoUtilException (String msg) {
        errorMessage = msg;
    }
}
