package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.Loan;
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
public class LoanRepositoryImpl implements LoanRepository {
    public static final String SQL_CREATE = "INSERT INTO LT_LOANS (LOAN_ID, CUSTOMER_ID, LOAN_AMOUNT" +
            ") VALUES (NEXTVAL('LT_LOAN_SEQ'), ?, ?)";

    public static final String SQL_FIND_BY_ID = "SELECT L.LOAN_ID, L.CUSTOMER_ID, L.LOAN_AMOUNT, L.DISBURSEMENT_DATE, "+
            "COALESCE(SUM(T.AMOUNT), 0) PAID_AMOUNT " +
            "FROM LT_LOAN_PAYMENTS T RIGHT OUTER JOIN LT_LOANS L ON L.LOAN_ID = T.LOAN_ID " +
            "WHERE L.CUSTOMER_ID = ? AND L.LOAN_ID = ? GROUP BY L.LOAN_ID";

    public static final String SQL_FIND_ALL = "SELECT L.LOAN_ID, L.CUSTOMER_ID, L.LOAN_AMOUNT, L.DISBURSEMENT_DATE, "+
            "COALESCE(SUM(T.AMOUNT), 0) PAID_AMOUNT " +
            "FROM LT_LOAN_PAYMENTS T RIGHT OUTER JOIN LT_LOANS L ON L.LOAN_ID = T.LOAN_ID " +
            "WHERE L.CUSTOMER_ID = ? GROUP BY L.LOAN_ID";

            @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Loan> findAll(Integer customerId) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[] {customerId}, loanRowMapper);
    }

    @Override
    public Loan findById(Integer customerId, Integer loanId) throws LtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {customerId, loanId},loanRowMapper);
        } catch(Exception e) {
           e.printStackTrace();
           return null;
           // throw new LtResourceNotFoundException("Loan not found");
        }
    }

    @Override
    public Integer create(Integer customerId, Double loanAmount) throws LtResourceNotFoundException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, customerId);
                ps.setDouble(2, loanAmount);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("LOAN_ID");
        } catch(Exception e) {
            throw new LtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void update(Integer customerId, Integer loanId, Loan loan) throws LtResourceNotFoundException {

    }

    @Override
    public void removeById(Integer customerId, Integer loanId) throws LtResourceNotFoundException {

    }

    private RowMapper<Loan> loanRowMapper = (((rs, rowNum) -> {
        return new Loan(rs.getInt("LOAN_ID"),
                rs.getInt("CUSTOMER_ID"),
                rs.getDouble("LOAN_AMOUNT"),
                rs.getDouble("LOAN_AMOUNT") - rs.getDouble("PAID_AMOUNT"),
                rs.getString("DISBURSEMENT_DATE"));
    }));
}
