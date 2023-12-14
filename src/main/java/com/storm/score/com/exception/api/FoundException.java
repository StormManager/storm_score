package com.storm.score.com.exception.api;
/**
 *
 */

import com.storm.score.com.exception.ApiException;

/**
 * description    :
 * packageName    : com.storm.score.com.exception.api
 * fileName       : FoundException
 * author         : wammelier
 * date           : 2023/12/14
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/14        wammelier       최초 생성
 */
public class FoundException extends ApiException {

  private String rspCode;
  private Object args;

  public FoundException(final String rspCode, Object... args){
    super(rspCode, args);
  }

}
