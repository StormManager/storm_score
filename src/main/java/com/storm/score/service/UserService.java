package com.storm.score.service;
/**
 *
 */

import com.storm.score.com.exception.ErrorCode;
import com.storm.score.com.exception.ExceptionFactory;
import com.storm.score.com.exception.api.ConflictException;
import com.storm.score.domain.user.User;
import com.storm.score.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description    :
 * packageName    : com.storm.score.service
 * fileName       : UserService
 * author         : wammelier
 * date           : 2023/12/14
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/14        wammelier       최초 생성
 */
@Service
public class UserService {

  @Autowired
  UserMapper userMapper;


  public User getUser() {
    return userMapper.getUser();
  }

  public void insertUser() {
    try {
      userMapper.insertUser();
    } catch(Exception e) {
      throw ExceptionFactory.getException(ErrorCode.E409);
    }
  }





}
