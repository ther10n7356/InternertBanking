package com.banking.internet_banking.port.input.http;

import com.banking.internet_banking.enums.OperationTypes;
import com.banking.internet_banking.model.MoneyRequestDTO;
import com.banking.internet_banking.model.OperationListResponseDTO;
import com.banking.internet_banking.model.ResultMessageDto;
import com.banking.internet_banking.model.TransferMoneyRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class  BankingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankingRestController bankingRestController;
    private ObjectWriter ow;
    private MoneyRequestDTO moneyRequest;
    private TransferMoneyRequestDTO transferMoneyRequest;
    private final UUID testUUID = UUID.fromString("38fabb96-0fc0-4ce8-b2bb-c6c90e957063");
    private final ResultMessageDto successBalanceResult = new ResultMessageDto("1", "99.99");
    private final ResultMessageDto successResult = new ResultMessageDto("1", "Ok");
    private final ResultMessageDto rejectResult = new ResultMessageDto("0", "Some errors");
    private final String testAmount = "1010";

    @BeforeEach
    private void init() {
        ow = new ObjectMapper()
                .findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .writer();
        moneyRequest = new MoneyRequestDTO(testUUID.toString(), testAmount);
        transferMoneyRequest = new TransferMoneyRequestDTO(testUUID.toString(), "358b7b44-7671-41fc-9679-20f4650d51ce", "1010");
    }

    @Test
    public void getBalanceResultTest() throws Exception {
        when(bankingRestController.getBalance(testUUID.toString())).thenReturn(ResponseEntity.ok(successBalanceResult));

        mockMvc.perform(get("/getBalance?userId=" + testUUID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(successBalanceResult)));
    }

    @Test
    public void postTakeMoneySuccessResultTest() throws Exception {
        when(bankingRestController.takeMoney(Mockito.any())).thenReturn(ResponseEntity.ok(successResult));

        mockMvc.perform(post("/takeMoney").content( ow.writeValueAsString(moneyRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(successResult)));
    }

    @Test
    public void postTakeMoneyRejectResultTest() throws Exception {
        when(bankingRestController.takeMoney(Mockito.any())).thenReturn(ResponseEntity.ok(rejectResult));

        mockMvc.perform(post("/takeMoney").content( ow.writeValueAsString(moneyRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(rejectResult)));
    }

    @Test
    public void postPutMoneySuccessResultTest() throws Exception {
        when(bankingRestController.putMoney(Mockito.any())).thenReturn(ResponseEntity.ok(successResult));

        mockMvc.perform(post("/putMoney").content( ow.writeValueAsString(moneyRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(successResult)));
    }

    @Test
    public void postPutMoneyRejectResultTest() throws Exception {
        when(bankingRestController.putMoney(Mockito.any())).thenReturn(ResponseEntity.ok(rejectResult));

        mockMvc.perform(post("/putMoney").content( ow.writeValueAsString(moneyRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(rejectResult)));
    }

    @Test
    public void postTransferMoneySuccessResultTest() throws Exception {
        when(bankingRestController.transferMoney(Mockito.any())).thenReturn(ResponseEntity.ok(successResult));

        mockMvc.perform(post("/transferMoney").content( ow.writeValueAsString(transferMoneyRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(successResult)));
    }

    @Test
    public void postTransferMoneyRejectResultTest() throws Exception {
        when(bankingRestController.transferMoney(Mockito.any())).thenReturn(ResponseEntity.ok(rejectResult));

        mockMvc.perform(post("/transferMoney").content( ow.writeValueAsString(transferMoneyRequest)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(rejectResult)));
    }

    @Test
    public void getOperationsListSuccessTest() throws Exception {
        List<OperationListResponseDTO> operations = List.of(
                new OperationListResponseDTO(LocalDateTime.now(), OperationTypes.PUT.toString(), Long.parseLong(testAmount)),
                new OperationListResponseDTO(LocalDateTime.now(), OperationTypes.TAKE.toString(), Long.parseLong(testAmount))
        );

        when(bankingRestController.getOperationList(any(), any(), any())).thenReturn(ResponseEntity.ok(operations));
        mockMvc.perform(get("/getOperationList?userId=" + testUUID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(ow.writeValueAsString(operations)));
    }
}
