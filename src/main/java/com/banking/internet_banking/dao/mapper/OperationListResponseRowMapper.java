package com.banking.internet_banking.dao.mapper;

import com.banking.internet_banking.enums.OperationTypes;
import com.banking.internet_banking.model.OperationListResponseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationListResponseRowMapper implements RowMapper<OperationListResponseDTO> {
    @Override
    public OperationListResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OperationListResponseDTO(
                rs.getTimestamp("oper_date").toLocalDateTime(),
                OperationTypes.valueOf(rs.getInt("oper_type")).getTypeName(),
                rs.getInt("oper_amount")
        );
    }
}
