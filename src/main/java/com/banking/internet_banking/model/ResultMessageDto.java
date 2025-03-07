package com.banking.internet_banking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultMessageDto {
    @JsonProperty("result")
    private final String result;
    @JsonProperty("message")
    private final String message;

    public ResultMessageDto(String result) {
        this(result, "Ok");
    }

    public ResultMessageDto(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
