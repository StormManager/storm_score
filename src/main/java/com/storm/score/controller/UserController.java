package com.storm.score.controller;

import io.swagger.annotations.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * description    : User Management System
 * packageName    : com.storm.score.controller
 * fileName       : UserController
 * author         : senor14
 * date           : 2023/11/23
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/11/23        senor14       최초 생성
 */
@RestController()
@RequestMapping("/api/users")
@Api("User Management System")
public class UserController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example = "- id: 1\n  name: 경태\n  email: kt123@example.com\n" +
                    "- id: 2\n  name: 승환\n  email: sh123@example.com\n" +
                    "- id: 3\n  name: 선열\n  email: sy123@example.com",
            dataType = "List"
    )
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
    @ApiOperation(value = "회원 목록 조회", notes = "사용 가능한 회원들의 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved all users",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value =
                                            "- id: 1\n  name: 경태\n  email: kt123@example.com\n" +
                                                    "- id: 2\n  name: 승환\n  email: sh123@example.com\n" +
                                                    "- id: 3\n  name: 선열\n  email: sy123@example.com"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userDatabase;
        if (userList != null) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "회원 정보 조회", notes = "특정 회원의 정보를 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 번호", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved user",
                    response = User.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  name: 경태\n  email: kt123@example.com"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        for (User user : userDatabase) {
            if (user.getId() == userId) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "회원 등록", notes = "새로운 회원을 등록")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created user",
                    response = User.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- id: 1\n  name: 경태\n  email: kt123@example.com"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    @ApiOperation(value = "회원 수정", notes = "회원의 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 번호", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "사용자 이름", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "이메일 주소", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated user",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "User updated successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    @ApiOperation(value = "회원 삭제", notes = "회원 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 번호", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully deleted user",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "User deleted successfully"
                            )
                    )
            ),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
