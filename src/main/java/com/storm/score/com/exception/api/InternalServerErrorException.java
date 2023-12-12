package com.storm.score.com.exception.api;
/**
 *
 */

import com.storm.score.com.exception.ApiException;

/**
 * description    :
 * packageName    : com.storm.score.com.exception.api
 * fileName       : InternalServerErrorException
 * author         : wammelier
 * date           : 2023/12/12
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/12        wammelier       최초 생성
 */
public class InternalServerErrorException extends ApiException {

  private String rspCode;
  private Object args;

  public InternalServerErrorException(final String rspCode, Object... args){
    super(rspCode, args);
  }

}
