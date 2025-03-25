package com.banking.internet_banking.controller;

import com.banking.internet_banking.dao.BankingDao;
import com.banking.internet_banking.enums.OperationTypes;
import com.banking.internet_banking.model.OperationListResponseDTO;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.MoneyRequestDTO;
import com.banking.internet_banking.model.TransferMoneyRequestDTO;
import com.sun.jdi.InternalException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class BankingController {
    private final Log log = LogFactory.getLog(this.getClass().getName());

    private final BankingDao bankingDao;

    @Autowired
    public BankingController(BankingDao bankingDao) {
        this.bankingDao = bankingDao;
    }


    public ResultMessageDto getBalance(String userId) {
        return new ResultMessageDto(bankingDao.getBalance(UUID.fromString(userId)).toString());
    }

    @Transactional
    public ResultMessageDto processMoney(MoneyRequestDTO moneyRequest, OperationTypes operationType) {

        processOperation(moneyRequest, operationType);
        return new ResultMessageDto("1");
    }

    @Transactional
    public ResultMessageDto transferMoney(TransferMoneyRequestDTO transferMoneyRequest) {
        UUID referenceId = UUID.randomUUID();
        processOperation(
                new MoneyRequestDTO(
                        transferMoneyRequest.getCurrentUserId(),
                        transferMoneyRequest.getTransferAmount()
                ),
                OperationTypes.TRANSFER_TO,
                referenceId);
        processOperation(
                new MoneyRequestDTO(
                        transferMoneyRequest.getDestinationUserId(),
                        transferMoneyRequest.getTransferAmount()
                ),
                OperationTypes.TRANSFER_FROM,
                referenceId);
        return new ResultMessageDto("1"); 
    }

    public List<OperationListResponseDTO> getOperations(String userId, String fromDate, String tillDate) {
        LocalDateTime dateFrom = null;
        LocalDateTime dateTill = null;
        try {
            if (fromDate != null && !fromDate.isEmpty()) {
                dateFrom = toLocalDate(fromDate);
            }

            if (tillDate != null && !tillDate.isEmpty()) {
                dateTill = toLocalDate(tillDate);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return List.of();
        }

        return bankingDao.getOperations(UUID.fromString(userId), dateFrom, dateTill);
    }

    private void processOperation(MoneyRequestDTO moneyRequest, OperationTypes operationType) {
        processOperation(moneyRequest, operationType, null);
    }

    private void processOperation(MoneyRequestDTO moneyRequest, OperationTypes operationType, UUID referenceID) {
        changeAmount(moneyRequest, operationType);
        addOperation(moneyRequest, operationType, referenceID);
    }

    private LocalDateTime toLocalDate(String dateValue) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.ofInstant(format.parse(dateValue).toInstant(), ZoneId.systemDefault());
    }

    private void changeAmount(MoneyRequestDTO transferMoney, OperationTypes type) {
        int currentBalance = bankingDao.getBalance(UUID.fromString(transferMoney.getUserId()));
        int transferAmount = Integer.parseInt(transferMoney.getAmount());

        int newBalance;

        switch (type) {
            case TAKE, TRANSFER_TO -> {
                if (currentBalance < transferAmount) {
                    throw new InternalException("Сумма перевода больше баланса");
                }
                newBalance = currentBalance - transferAmount;
            }
            case PUT, TRANSFER_FROM -> {
                newBalance = currentBalance + transferAmount;
            }
            default -> throw new UnsupportedOperationException("Неизвестный тип операции");
        }

        bankingDao.changeBalance(UUID.fromString(transferMoney.getUserId()), newBalance);
    }

    private void addOperation(MoneyRequestDTO transferMoneyDTO, OperationTypes operType, UUID referenceId) {
        bankingDao.addOperation(
                UUID.randomUUID(),
                UUID.fromString(transferMoneyDTO.getUserId()),
                LocalDateTime.now(),
                operType.getOperTypeIndex(),
                Integer.parseInt(transferMoneyDTO.getAmount()),
                referenceId
        );
    }
}
