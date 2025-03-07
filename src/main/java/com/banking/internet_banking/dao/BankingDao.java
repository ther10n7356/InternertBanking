package com.banking.internet_banking.dao;

import java.util.UUID;

public interface BankingDao {

    Double getBalance(UUID userId);

    void changeBalance(UUID userId, double newBalance);
}
