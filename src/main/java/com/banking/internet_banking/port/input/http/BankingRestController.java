package com.banking.internet_banking.port.input.http;

import com.banking.internet_banking.controller.BankingController;
import com.banking.internet_banking.enums.OperationTypes;
import com.banking.internet_banking.model.OperationListResponseDTO;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.MoneyRequestDTO;
import com.banking.internet_banking.model.TransferMoneyRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankingRestController {
    private final BankingController bankingController;

    @Autowired
    public BankingRestController(BankingController bankingController) {
        this.bankingController = bankingController;
    }

    @GetMapping("/getBalance")
    public ResponseEntity<ResultMessageDto> getBalance(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(bankingController.getBalance(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResultMessageDto("0", e.getMessage()));
        }
    }

    @PostMapping("/takeMoney")
    public ResponseEntity<ResultMessageDto> takeMoney(@RequestBody MoneyRequestDTO moneyRequest) {
        try {
            return ResponseEntity.ok(bankingController.processMoney(moneyRequest, OperationTypes.TAKE));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResultMessageDto("0", e.getMessage()));
        }
    }

    @PostMapping("/putMoney")
    public ResponseEntity<ResultMessageDto> putMoney(@RequestBody MoneyRequestDTO moneyRequest) {
        try {
            return ResponseEntity.ok(bankingController.processMoney(moneyRequest, OperationTypes.PUT));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResultMessageDto("0", e.getMessage()));
        }
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<ResultMessageDto> transferMoney(@RequestBody TransferMoneyRequestDTO transferMoneyRequest) {
        try {
            return ResponseEntity.ok(bankingController.transferMoney(transferMoneyRequest));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResultMessageDto("0", e.getMessage()));
        }
    }

    @GetMapping("/getOperationList")
    public ResponseEntity<List<OperationListResponseDTO>> getOperationList(
            @RequestParam String userId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String tillDate
    ) {
        return ResponseEntity.ok(bankingController.getOperations(userId, fromDate, tillDate));
    }

}
