package com.banking.internet_banking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferMoneyRequestDTO {
    @JsonProperty("currentUserId")
    private final String currentUserId;
    @JsonProperty("destinationUserId")
    private final String destinationUserId;
    @JsonProperty("amount")
    private final String transferAmount;

    public TransferMoneyRequestDTO(String currentUserId, String destinationUserId, String transferAmount) {
        this.currentUserId = currentUserId;
        this.destinationUserId = destinationUserId;
        this.transferAmount = transferAmount;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getDestinationUserId() {
        return destinationUserId;
    }

    public String getTransferAmount() {
        return transferAmount;
    }
}
