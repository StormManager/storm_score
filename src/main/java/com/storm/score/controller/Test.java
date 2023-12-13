package com.storm.score.controller;

import com.storm.score.com.exception.ApiException;
import com.storm.score.com.exception.ErrorCode;
import com.storm.score.com.exception.ExceptionFactory;
import com.storm.score.com.exception.api.ConflictException;
import com.storm.score.domain.user.User;
import com.storm.score.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

@RestController
@Api("테스트용도")
public class Test {

  @Autowired
  UserService userService;


  @GetMapping("/test")
  @ApiOperation(value="HTTP:GET", notes = "TEST")
  @ApiImplicitParams({
          @ApiImplicitParam(name="userName", value = "회원의이름", required = true, dataType = "String")
  })
  public String insertUser(@RequestParam String userName) throws Exception{
    userService.insertUser();

    return "success";
  }





}
