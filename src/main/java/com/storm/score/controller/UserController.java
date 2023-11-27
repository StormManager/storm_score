package com.storm.score.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api/users")
public class UserController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    private static final List<User> userDatabase = new ArrayList<>();

    private static long userId = 0;

    static {
        userDatabase.add(new User(++userId, "경태", "kt123@example.com"));
        userDatabase.add(new User(++userId, "승환", "sh123@example.com"));
        userDatabase.add(new User(++userId, "선열", "sy123@example.com"));
    }

    @Getter
    @Setter
    private static class User {
        private Long id;
        private String name;
        private String email;

        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
    }

    // ####################################

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userDatabase;
        if (userList != null) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        for (User user : userDatabase) {
            if (user.getId() == userId) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestParam String name, @RequestParam String email) {
        userDatabase.add(new User(++userId, name, email));
        for (User user : userDatabase) {
            if (user.getId() == userId) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserById(@PathVariable Long userId,
                                                 @RequestParam String name,
                                                 @RequestParam String email) {
        for (User user : userDatabase) {
            if (user.getId() == userId) {
                user.setName(name);
                user.setEmail(email);
                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        for (User user : userDatabase) {
            if (user.getId() == userId) {
                userDatabase.remove(user);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
