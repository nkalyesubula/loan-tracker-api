package com.knoah.loantrackerapi.resources;

import com.knoah.loantrackerapi.Constants;
import com.knoah.loantrackerapi.domain.User;
import com.knoah.loantrackerapi.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "Login User")
    public ResponseEntity<Map<String, String>> loginUser(@Schema(implementation=User.class,
            requiredProperties = {"email", "password"}) @RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email,password);
        Map<String, String> map = new HashMap<>();
        map.put("message", "loggedIn successfully");
        map.put("token",generateJWTToken(user));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // register the user
    @PostMapping("/register")
    @Operation(summary = "Create User")
    public ResponseEntity<Map<String, String>> registerUser(@Schema(implementation=User.class,
            requiredProperties = {"firstName", "lastName", "email", "password", "isAdmin"}) @RequestBody Map<String, Object> userMap) {
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        Boolean isAdmin = (Boolean) userMap.get("isAdmin");
        User user = userService.registerUser(firstName, lastName, email, password, isAdmin);
        Map<String, String> map = new HashMap<>();
        map.put("message", "registered successfully");
        System.out.println("helo");

        map.put("token", generateJWTToken(user));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private String generateJWTToken(User user) {
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
//        Map<String, String> map = new HashMap<>();
//        map.put("token", token);
        return token;
    }
}
