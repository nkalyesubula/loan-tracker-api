package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.LoanPayment;
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
public class LoanPaymentRepositoryImpl implements LoanPaymentRepository {
    private static final String SQL_CREATE = "INSERT INTO LT_LOAN_PAYMENTS (LOAN_PAYMENT_ID, CUSTOMER_ID, LOAN_ID, " +
            "AMOUNT, NOTE) VALUES(NEXTVAL('LT_LOAN_PAYMENTS_SEQ'), ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT LOAN_PAYMENT_ID, CUSTOMER_ID, LOAN_ID," +
            "AMOUNT, NOTE, PAYMENT_DATE FROM LT_LOAN_PAYMENTS WHERE CUSTOMER_ID = ? AND LOAN_ID = ? " +
            "AND LOAN_PAYMENT_ID = ?";

    private static final String SQL_FIND_ALL = "SELECT LOAN_PAYMENT_ID, CUSTOMER_ID, LOAN_ID," +
            "AMOUNT, NOTE, PAYMENT_DATE FROM LT_LOAN_PAYMENTS WHERE CUSTOMER_ID = ? AND LOAN_ID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<LoanPayment> findAll(Integer customerId, Integer loanId) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{customerId, loanId}, loanPaymentRowMapper);
    }

    @Override
    public LoanPayment findById(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {customerId, loanId, loanPaymentId}, loanPaymentRowMapper);
        } catch(Exception e) {
            throw new LtResourceNotFoundException("Loan Payment not found");
        }
    }

    @Override
    public Integer create(Integer customerId, Integer loanId, Double amount, String note) throws LtResourceNotFoundException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, customerId);
                ps.setInt(2, loanId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("LOAN_PAYMENT_ID");
        } catch(Exception e) {
            throw new LtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer customerId, Integer loanId, Integer loanPaymentId, LoanPayment loanPayment) throws LtResourceNotFoundException {

    }

    @Override
    public void removeById(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException {

    }
    private RowMapper<LoanPayment> loanPaymentRowMapper = (((rs, rowNum) -> {
        return new LoanPayment(rs.getInt("LOAN_PAYMENT_ID"),
                rs.getInt("LOAN_ID"),
                rs.getInt("CUSTOMER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getString("PAYMENT_DATE"));
    }));
}
