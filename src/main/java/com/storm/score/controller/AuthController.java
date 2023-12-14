package com.storm.score.controller;

import com.storm.score.domain.user.User;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description    :
 * packageName    : com.storm.score.controller
 * fileName       : AuthController
 * author         : senor14
 * date           : 2023-12-14
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-12-14        senor14       최초 생성
 */
@RestController
@RequestMapping("/api/v1/auth")
@Api("Auth Management System")
@ApiResponses(value = {
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class AuthController {

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", required = true, dataType = "Long")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully signed up",
                    response = com.storm.score.domain.user.User.class,
                    examples = @Example(
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "- userId: 1\n  nickname: 경태\n  email: kt123@example.com\n"
                            )
                    )
            )
    })
    public ResponseEntity<User> login(@RequestBody com.storm.score.domain.user.User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃", notes = "로그아웃")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 204,
                    message = "No Content"
            )
    })
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/token/refresh")
    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급")
    public ResponseEntity<?> refreshAccessToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
