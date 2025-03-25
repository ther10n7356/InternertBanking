package com.banking.internet_banking.controller;

import com.banking.internet_banking.dao.BankingDao;
import com.banking.internet_banking.enums.OperationTypes;
import com.banking.internet_banking.model.MoneyRequestDTO;
import com.banking.internet_banking.model.OperationListResponseDTO;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.TransferMoneyRequestDTO;
import com.sun.jdi.InternalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class BankingControllerTest {

    private final BankingDao bankingDao = mock(BankingDao.class);
    @InjectMocks
    private BankingController bankingController;

    private MoneyRequestDTO moneyRequest;
    private TransferMoneyRequestDTO transferMoneyRequest;

    private final UUID testUUID = UUID.fromString("38fabb96-0fc0-4ce8-b2bb-c6c90e957063");
    private final UUID testDestUUID = UUID.fromString("358b7b44-7671-41fc-9679-20f4650d51ce");
    private final String testAmount = "1010";
    private final int testBalance = 9999;

    @BeforeEach
    private void init() {
        moneyRequest = new MoneyRequestDTO(testUUID.toString(), testAmount);
        transferMoneyRequest = new TransferMoneyRequestDTO(
                testUUID.toString(),
                testDestUUID.toString(),
                testAmount
        );
        Mockito.when(bankingDao.getBalance(testUUID)).thenReturn(testBalance);
    }

    @Test
    public void getUserBalanceTest() {
        int balance = bankingDao.getBalance(testUUID);

        Assertions.assertEquals(balance, testBalance);
    }

    @Test
    public void takeMoneySuccessTest() {
       ResultMessageDto result = bankingController.processMoney(moneyRequest, OperationTypes.TAKE);

       Assertions.assertEquals(result.getResult(), "1");
    }

    @Test
    public void putMoneySuccessTest() {
        ResultMessageDto result = bankingController.processMoney(moneyRequest, OperationTypes.PUT);

        Assertions.assertEquals(result.getResult(), "1");
    }

    @Test
    public void transferMoneySuccessTest() {
        ResultMessageDto result = bankingController.transferMoney(transferMoneyRequest);

        Assertions.assertEquals(result.getResult(), "1");
    }

    @Test
    public void takeMoneyFailTest() {
        Mockito.when(bankingDao.getBalance(testUUID)).thenReturn(999);
        Assertions.assertThrows(InternalException.class, () ->  bankingController.processMoney(moneyRequest, OperationTypes.TAKE));
    }

    @Test
    public void putMoneyFailTest() {
        Mockito.doThrow(new Exception("Some errors")).doNothing().when(bankingDao);
        Assertions.assertThrows(Exception.class, () ->  bankingController.processMoney(moneyRequest, OperationTypes.PUT));
    }

    @Test
    public void transferMoneyFailTest() {
        Mockito.when(bankingDao.getBalance(testUUID)).thenReturn(999);
        Assertions.assertThrows(InternalException.class, () ->  bankingController.transferMoney(transferMoneyRequest));
    }

    @Test
    public void getOperationListTest() {
        List<OperationListResponseDTO> operations = List.of(
                new OperationListResponseDTO(LocalDateTime.now(), OperationTypes.PUT.getTypeName(), Long.parseLong(testAmount)),
                new OperationListResponseDTO(LocalDateTime.now(), OperationTypes.TAKE.toString(), Long.parseLong(testAmount))
        );

        Mockito.when(bankingDao.getOperations(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(operations);

        List<OperationListResponseDTO> result = bankingController.getOperations(testUUID.toString(), null, null);

        Assertions.assertEquals(operations.get(0).getOperationType(), result.get(0).getOperationType());
        Assertions.assertEquals(operations.get(0).getOperationDate(), result.get(0).getOperationDate());
        Assertions.assertEquals(operations.get(0).getOperationAmount(), result.get(0).getOperationAmount());
    }
}
