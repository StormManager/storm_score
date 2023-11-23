package com.storm.score.controller;
/**
 *
 */

/**
 * description    :
 * packageName    : com.storm.score.controller
 * fileName       : UserController
 * author         : wammelier
 * date           : 2023/11/23
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/11/23        wammelier       최초 생성
 */

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("테스트용도")
public class UserController {


  @ApiOperation(value="HTTP:GET", notes = "TEST")
  @ApiImplicitParams({
      @ApiImplicitParam(name="userName", value = "회원의이름", required = true, dataType = "String"),
      @ApiImplicitParam(name="userId", value = "회원의아이디", required = true, dataType = "String")
  })
  public User login(User user) {
   return user
  }

}
