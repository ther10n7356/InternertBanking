package com.banking.internet_banking.dao.impl;

import com.banking.internet_banking.dao.BankingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    public Double getBalance(UUID userId) {
        return jdbcTemplate.queryForObject(
                "select balance from user_balance where user_id = :user_id",
                Map.of("user_id", userId),
                Double.class
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
}
