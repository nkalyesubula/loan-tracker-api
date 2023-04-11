package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.User;
import com.knoah.loantrackerapi.exceptions.LtAuthException;
import com.knoah.loantrackerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public User validateUser(String email, String password) throws LtAuthException {
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password, Boolean isAdmin) throws LtAuthException {
        Pattern pattern  = Pattern.compile("^(.+)@(.+)$");
        if(email!=null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new LtAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new LtAuthException("Email already in use");
        Integer userId = userRepository.create(firstName, lastName, email, password, isAdmin);
        return userRepository.findById(userId);
    }
}
