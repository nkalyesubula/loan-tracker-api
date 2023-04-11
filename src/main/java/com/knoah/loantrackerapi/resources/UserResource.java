package com.knoah.loantrackerapi.resources;

import com.knoah.loantrackerapi.Constants;
import com.knoah.loantrackerapi.domain.User;
import com.knoah.loantrackerapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {
    @Autowired
    UserService userService;

    // sign in user
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email,password);
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "loggedIn successfully");
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    // register the user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        Boolean isAdmin = (Boolean) userMap.get("isAdmin");
        User user = userService.registerUser(firstName, lastName, email, password, isAdmin);
//        Map<String, String> map = new HashMap<>();
//        map.put("message", "registered successfully");
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    private Map<String,String> generateJWTToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("isAdmin", user.getAdmin())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }
}
