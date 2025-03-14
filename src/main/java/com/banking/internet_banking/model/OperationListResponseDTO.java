package com.banking.internet_banking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class OperationListResponseDTO {
    @JsonProperty("operationDate")
    private final LocalDateTime operationDate;
    @JsonProperty("operationType")
    private final String operationType;
    @JsonProperty("operationAmount")
    private final long operationAmount;

    public OperationListResponseDTO(LocalDateTime operationDate, String operationType, long operationAmount) {
        this.operationDate = operationDate;
        this.operationType = operationType;
        this.operationAmount = operationAmount;
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public String getOperationType() {
        return operationType;
    }

    public long getOperationAmount() {
        return operationAmount;
    }
}
