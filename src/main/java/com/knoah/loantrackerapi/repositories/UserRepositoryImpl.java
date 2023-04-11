package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.User;
import com.knoah.loantrackerapi.exceptions.LtAuthException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String SQL_CREATE = "INSERT INTO LT_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, " +
            "PASSWORD, IS_ADMIN) VALUES(NEXTVAL('LT_USERS_SEQ'), ?, ?, ?, ?, ?)";

    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM LT_USERS WHERE EMAIL = ?";

    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, IS_ADMIN " +
            "FROM LT_USERS WHERE USER_ID  = ?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, IS_ADMIN " +
            "FROM LT_USERS WHERE EMAIL = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(String firstName, String lastName, String email, String password, Boolean isAdmin) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                ps.setBoolean(5,isAdmin);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_ID");
        } catch(Exception e) {
            throw new LtAuthException("Invalid details. Failed to create account");
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object [] {email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new LtAuthException("Invalid email/password");
            return user;
        } catch (Exception e) {
            throw new LtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[] {email}, Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object [] {userId}, userRowMapper);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getInt("USER_ID"),
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getBoolean("IS_ADMIN")
        );
    });
}
