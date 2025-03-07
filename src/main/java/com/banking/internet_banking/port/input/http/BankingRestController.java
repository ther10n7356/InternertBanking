package com.banking.internet_banking.port.input.http;

import com.banking.internet_banking.controller.BankingController;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.TransferMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BankingRestController {
    private final BankingController bankingController;

    @Autowired
    public BankingRestController(BankingController bankingController) {
        this.bankingController = bankingController;
    }

    @GetMapping("/getBalance")
    public ResponseEntity<ResultMessageDto> getBalance(@RequestParam String userId) {
        return ResponseEntity.ok(bankingController.getBalance(userId));
    }

    @PostMapping("/takeMoney")
    public ResponseEntity<ResultMessageDto> takeMoney(@RequestBody TransferMoney transferMoney) {
        return ResponseEntity.ok(bankingController.takeMoney(transferMoney));
    }

    @PostMapping("/putMoney")
    public ResponseEntity<ResultMessageDto> putMoney(@RequestBody TransferMoney transferMoney) {
        return ResponseEntity.ok(bankingController.putMoney(transferMoney));
    }
}
