package com.storm.score.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("테스트용도")
public class Test {

  @GetMapping("/test")
  @ApiOperation(value="HTTP:GET", notes = "TEST")
  @ApiImplicitParams({
      @ApiImplicitParam(name="userName", value = "회원의이름", required = true, dataType = "String"),
      @ApiImplicitParam(name="userId", value = "회원의아이디", required = true, dataType = "String")
  })
  public String getTest(@RequestParam String userName){
    if(userName.equals(userName))
     return "HIHI";
    else
      return "FAIL";

  }

}
