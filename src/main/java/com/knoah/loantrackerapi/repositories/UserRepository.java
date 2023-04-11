package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.User;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password, Boolean isAdmin);

    User findByEmailAndPassword(String email, String password);

    Integer getCountByEmail(String email);

    User findById(Integer userId);
}
