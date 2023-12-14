package com.storm.score.com.exception.api;
/**
 *
 */

import com.storm.score.com.exception.ApiException;

/**
 * description    :
 * packageName    : com.storm.score.com.exception.api
 * fileName       : TooManyRequestException
 * author         : wammelier
 * date           : 2023/12/14
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/14        wammelier       최초 생성
 */
public class TooManyRequestException extends ApiException {

  private String rspCode;
  private Object args;

  public TooManyRequestException(final String rspCode, Object... args) {
    super(rspCode, args);
  }

}
