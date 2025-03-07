package com.banking.internet_banking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferMoney {
    @JsonProperty("userId")
    private final String userId;
    @JsonProperty("amount")
    private final String amount;

    public TransferMoney(String userId, String amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public String getAmount() {
        return amount;
    }
}
