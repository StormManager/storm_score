package com.storm.score.controller;

import com.storm.score.domain.notification.Notification;
import com.storm.score.domain.user.User;
import io.swagger.annotations.*;
import lombok.Builder;
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
@RequestMapping("/api/v1/users")
@Api("User Management System")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class UserController {

    //  ####### 도메인 추가시 삭제 요망#######
    // 임시 데이터 저장소
    @ApiModelProperty(
            example = "- userId: 1\n  nickname: 경태\n  email: kt123@example.com\n" +
                    "- userId: 2\n  nickname: 승환\n  email: sh123@example.com\n" +
                    "- userId: 3\n  nickname: 선열\n  email: sy123@example.com",
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
    @Builder
    private static class User {
        private Long userId;
        private String nickname;
        private String email;
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
                                            "- userId: 1\n  nickname: 경태\n  email: kt123@example.com\n" +
                                                    "- userId: 2\n  nickname: 승환\n  email: sh123@example.com\n" +
                                                    "- userId: 3\n  nickname: 선열\n  email: sy123@example.com"
                            )
                    )
            )
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
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved user",
                    response = User.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- userId: 1\n  nickname: 경태\n  email: kt123@example.com"
                            )
                    )
            )
    })
    public ResponseEntity<User> getUserByUserId(@PathVariable Long userId) {
        for (User user : userDatabase) {
            if (user.getUserId() == userId) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    @ApiOperation(value = "회원 등록", notes = "새로운 회원을 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "회원 닉네임", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "이메일 주소", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully created user",
                    response = User.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- userId: 1\n  nickname: 경태\n  email: kt123@example.com"
                            )
                    )
            )
    })
    public ResponseEntity<User> createUser(@RequestParam String nickname, @RequestParam String email) {
        userDatabase.add(User
                .builder()
                .userId(++userId)
                .nickname(nickname)
                .email(email)
                .build());
        for (User user : userDatabase) {
            if (user.getUserId() == userId) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "회원 수정", notes = "회원의 정보를 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "nickname", value = "회원 닉네임", required = true, dataType = "String"),
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
            )
    })
    public ResponseEntity<String> updateUserByUserId(@PathVariable Long userId,
                                                     @RequestParam String nickname,
                                                     @RequestParam String email) {
        for (User user : userDatabase) {
            if (user.getUserId() == userId) {
                user.setNickname(nickname);
                user.setEmail(email);
                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    @ApiOperation(value = "회원 삭제", notes = "회원 정보를 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long")
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
            )
    })
    public ResponseEntity<String> deleteUserByUserId(@PathVariable Long userId) {
        for (User user : userDatabase) {
            if (user.getUserId() == userId) {
                userDatabase.remove(user);
                return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}/unread-notifications")
    @ApiOperation(value = "사용자의 안 읽은 알림 목록 조회", notes = "사용자의 안 읽은 알림 목록을 시간 내림차순으로 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved user's unread notifications",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- notificationId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
                                            "- notificationId: 2\n  inviteId: 2\n  userId: 1\n  status: UNREAD\n" +
                                            "- notificationId: 3\n  inviteId: 3\n  userId: 1\n  status: UNREAD"
                            )
                    )
            )
    })
    public ResponseEntity<List<Notification>> getUnreadNotificationsSortedByTime(@PathVariable Long userId) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/notifications")
    @ApiOperation(value = "사용자의 전체 알림 목록 조회", notes = "사용자의 전체 알림 목록을 시간 내림차순으로 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved user's notifications",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- notificationId: 1\n  inviteId: 1\n  userId: 1\n,  status: UNREAD\n" +
                                            "- notificationId: 2\n  inviteId: 2\n  userId: 1\n  status: UNREAD\n" +
                                            "- notificationId: 3\n  inviteId: 3\n  userId: 1\n  status: READ"
                            )
                    )
            )
    })
    public ResponseEntity<List<Notification>> getNotificationsSortedByTime(@PathVariable Long userId) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ApiOperation(value = "사용자의 닉네임으로 사용자 목록 조회", notes = "사용자의 닉네임으로 사용자 목록 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "회원 닉네임", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved users",
                    response = List.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- userId: 1\n  nickname: 경태\n  email: kt123@example.com\n"
                            )
                    )
            )
    })
    public ResponseEntity<List<com.storm.score.domain.user.User>> searchUserByNickname(@RequestParam String nickname) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PutMapping("/{userId}/nickname")
    @ApiOperation(value = "사용자의 닉네임 수정", notes = "사용자의 닉네임을 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "nickname", value = "회원 닉네임", required = true, dataType = "String")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully updated user's nickname",
                    response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "User's nickname updated successfully"
                            )
                    )
            )
    })
    public ResponseEntity<String> updateUserNickname(@PathVariable Long userId,
                                                     @RequestParam String nickname) {
        return new ResponseEntity<>("User's nickname updated successfully", HttpStatus.OK);
    }
}
