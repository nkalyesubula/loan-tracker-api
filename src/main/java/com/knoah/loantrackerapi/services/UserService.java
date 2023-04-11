package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.User;
import com.knoah.loantrackerapi.exceptions.LtAuthException;

public interface UserService {
    User validateUser(String email, String password) throws LtAuthException;

    User registerUser(String firstName, String lastName, String email, String password, Boolean isAdmin) throws LtAuthException;
}
