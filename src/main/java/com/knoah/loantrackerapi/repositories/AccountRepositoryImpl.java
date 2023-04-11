package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.Account;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    public static final String SQL_FIND_ALL = "SELECT ACCOUNT_ID, CUSTOMER_ID, OPENING_BALANCE FROM " +
            "LT_ACCOUNTS WHERE CUSTOMER_ID = ?";
    public static final String SQL_FIND_BY_ID = "SELECT ACCOUNT_ID, CUSTOMER_ID, OPENING_BALANCE FROM " +
            "LT_ACCOUNTS WHERE CUSTOMER_ID = ? AND ACCOUNT_ID =?";
    public static final String SQL_CREATE = "INSERT INTO LT_ACCOUNTS (ACCOUNT_ID, CUSTOMER_ID, OPENING_BALANCE) " +
            "VALUES (NEXTVAL('LT_ACCOUNTS_SEQ'), ?, ?)";

    private static final String SQL_UPDATE = "UPDATE LT_ACCOUNTS SET OPENING_BALANCE = ? "+
            "WHERE CUSTOMER_ID = ? AND ACCOUNT_ID = ?";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM LT_ACCOUNTS WHERE CUSTOMER_ID = ? AND " +
            "ACCOUNT_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Account> findAll(Integer customerId) throws LtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[] {customerId}, accountRowMapper);
    }

    @Override
    public Account findById(Integer customerId, Integer accountId) throws LtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {customerId, accountId}, accountRowMapper);
        } catch(Exception e) {
            throw new LtResourceNotFoundException("Account not found");
        }
    }

    @Override
    public Integer create(Integer customerId, Double openingBalance) throws LtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, customerId);
                ps.setDouble(2, openingBalance);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("ACCOUNT_ID");
        } catch(Exception e) {
            throw new LtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void update(Integer customerId, Integer accountId, Account account) throws LtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{account.getOpeningBalance(), customerId, accountId});
        } catch (Exception e) {
            throw new LtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void removeById(Integer customerId, Integer accountId) {
        jdbcTemplate.update(SQL_DELETE_ACCOUNT, new Object[] {customerId, accountId});
    }

    private RowMapper<Account> accountRowMapper = (((rs, rowNum) -> {
        return new Account(rs.getInt("ACCOUNT_ID"),
                rs.getInt("CUSTOMER_ID"),
                rs.getDouble("OPENING_BALANCE"));
    }));
}
