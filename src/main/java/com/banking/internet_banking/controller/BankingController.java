package com.banking.internet_banking.controller;

import com.banking.internet_banking.dao.BankingDao;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.TransferMoney;
import com.sun.jdi.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankingController {

    private BankingDao bankingDao;

    @Autowired
    public BankingController(BankingDao bankingDao) {
        this.bankingDao = bankingDao;
    }

    public ResultMessageDto getBalance(String userId) {
        try {
            return new ResultMessageDto(bankingDao.getBalance(UUID.fromString(userId)).toString());
        } catch (Exception e) {
            return new ResultMessageDto("-1", e.getMessage());
        }
    }

    public ResultMessageDto takeMoney(TransferMoney transferMoney) {
        try {
            changeAmount(transferMoney, "transfer");
            return new ResultMessageDto("1");
        } catch (Exception e) {
            return new ResultMessageDto("0", e.getMessage());
        }
    }

    public ResultMessageDto putMoney(TransferMoney transferMoney) {
        try {
            changeAmount(transferMoney, "put");
            return new ResultMessageDto("1");
        } catch (Exception e) {
            return new ResultMessageDto("0", e.getMessage());
        }
    }

    private void changeAmount(TransferMoney transferMoney, String action) {
        double currentBalance = bankingDao.getBalance(UUID.fromString(transferMoney.getUserId()));
        double transferAmount = Double.parseDouble(transferMoney.getAmount());

        double newBalance;

        switch (action) {
            case "transfer" -> {
                if (currentBalance < transferAmount) {
                    throw new InternalException("Сумма перевода больше баланса");
                }
                newBalance = currentBalance - transferAmount;
            }
            case "put" -> {
                newBalance = currentBalance + transferAmount;
            }
            default -> throw new UnsupportedOperationException("Неизвестный тип операции");
        }

        bankingDao.changeBalance(UUID.fromString(transferMoney.getUserId()), newBalance);
    }
}
