package com.storm.score.com.exception.api;
/**
 *
 */

import com.storm.score.com.exception.ApiException;

/**
 * description    :
 * packageName    : com.storm.score.com.exception.api
 * fileName       : BadRequestException
 * author         : wammelier
 * date           : 2023/12/10
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        wammelier       최초 생성
 */
public class BadRequestException extends ApiException {
  private static final long serialVersionUID = 1L;


  public BadRequestException(final String rspCode, final Object... args) {
    super(rspCode, args);
  }

}
