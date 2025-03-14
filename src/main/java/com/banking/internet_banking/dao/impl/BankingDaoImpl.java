package com.banking.internet_banking.dao.impl;

import com.banking.internet_banking.dao.BankingDao;
import com.banking.internet_banking.dao.mapper.OperationListResponseRowMapper;
import com.banking.internet_banking.model.OperationListResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BankingDaoImpl implements BankingDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BankingDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Integer getBalance(UUID userId) {
        return jdbcTemplate.queryForObject(
                "select balance from user_balance where user_id = :user_id",
                Map.of("user_id", userId),
                Integer.class
        );
    }

    @Override
    public void changeBalance(UUID userId, double newBalance) {
        jdbcTemplate.update(
                "update user_balance set balance = :new_balance where user_id = :user_id",
                Map.of(
                        "user_id", userId,
                        "new_balance", newBalance
                )
        );
    }

    @Override
    public void addOperation(UUID operationId, UUID userId, LocalDateTime operationDate, int operationType, int operationAmount) {
        jdbcTemplate.update(
                "insert\n" +
                        "\tinto\n" +
                        "\toperations (oper_id,\n" +
                        "\tuser_id,\n" +
                        "\toper_date,\n" +
                        "\toper_type,\n" +
                        "\toper_amount)\n" +
                        "values (:oper_id,\n" +
                        ":user_id,\n" +
                        ":oper_date,\n" +
                        ":oper_type,\n" +
                        ":oper_amount)",
                Map.of(
                        "oper_id", operationId,
                        "user_id", userId,
                        "oper_date", operationDate,
                        "oper_type", operationType,
                        "oper_amount", operationAmount
                )
        );
    }

    @Override
    public List<OperationListResponseDTO> getOperations(UUID userId, LocalDateTime fromDate, LocalDateTime tillDate) {
        StringBuilder sqlText = new StringBuilder("select oper_date, oper_type, oper_amount from operations where user_id = :user_id");
        if (fromDate != null) {
            sqlText.append(" and oper_date >= :from_date");
        }

        if (tillDate != null) {
            sqlText.append(" and oper_date < :till_date");
        }
        return jdbcTemplate.query(
                sqlText.toString(),
                Map.of(
                        "user_id", userId,
                        "from_date", (fromDate == null) ? LocalDateTime.now():fromDate,
                        "till_date", (tillDate == null) ? LocalDateTime.now():tillDate
                ),
                new OperationListResponseRowMapper()
        );
    }
}
