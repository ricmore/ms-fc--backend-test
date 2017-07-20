package com.scmspain.controller.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Holds error code and message.
 *
 * @author ricardmore
 */
public class ErrorResponse implements Serializable {

    @JsonProperty
    private int erroCode;
    @JsonProperty
    private String errorMessage;

    public ErrorResponse(int erroCode, String errorMessage) {
        this.erroCode = erroCode;
        this.errorMessage = errorMessage;
    }

    public int getErroCode() {
        return erroCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
