package com.banking.internet_banking.port.input.http;

import com.banking.internet_banking.controller.BankingController;
import com.banking.internet_banking.model.OperationListResponseDTO;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.TransferMoneyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        return ResponseEntity.ok(bankingController.getBalance(userId));
    }

    @PostMapping("/takeMoney")
    public ResponseEntity<ResultMessageDto> takeMoney(@RequestBody TransferMoneyDTO transferMoney) {
        return ResponseEntity.ok(bankingController.takeMoney(transferMoney));
    }

    @PostMapping("/putMoney")
    public ResponseEntity<ResultMessageDto> putMoney(@RequestBody TransferMoneyDTO transferMoney) {
        return ResponseEntity.ok(bankingController.putMoney(transferMoney));
    }

    @GetMapping("/getOperationList")
    public ResponseEntity<List<OperationListResponseDTO>> getOperationList(
            @RequestParam String userId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String tillDate
    )  {
        return ResponseEntity.ok(bankingController.getOperations(userId, fromDate, tillDate));
    }

}
