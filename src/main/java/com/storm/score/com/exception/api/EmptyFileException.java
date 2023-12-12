package com.storm.score.com.exception.api;
/**
 *
 */

import com.storm.score.com.exception.ApiException;

/**
 * description    :
 * packageName    : com.storm.score.com.exception.api
 * fileName       : EmptyFileException
 * author         : wammelier
 * date           : 2023/12/10
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        wammelier       최초 생성
 */
public class EmptyFileException extends ApiException {
  private static final long serialVersionUID = 1L;

  public EmptyFileException(final String rspCode, Object... args) {
    super(rspCode, args);
  }



}
