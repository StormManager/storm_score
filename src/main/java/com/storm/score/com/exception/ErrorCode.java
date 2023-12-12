package com.storm.score.com.exception;
/**
 *
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * description    :
 * packageName    : com.storm.score.exception
 * fileName       : ErrorCode
 * author         : wammelier
 * date           : 2023/12/05
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/05        wammelier       최초 생성
 */
@AllArgsConstructor
public enum ErrorCode {
  E001("001"),
  E401("401"),
  E402("402"),
  E403("403"),
  E404("404"),
  E405("405"),
  E406("406"),
  E409("409"),
  E501("501"),
  E502("502"),
  E503("503"),
  E504("504"),
  E505("505");


  @Getter
  private String code;

}