package com.banking.internet_banking.dao;

import com.banking.internet_banking.model.OperationListResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BankingDao {

    Integer getBalance(UUID userId);

    void changeBalance(UUID userId, double newBalance);

    void addOperation(UUID operationId, UUID userId, LocalDateTime operationDate, int operationType, int operationAmount, UUID referenceId );

    List<OperationListResponseDTO> getOperations(UUID userId, LocalDateTime fromDate, LocalDateTime tillDate);
}
