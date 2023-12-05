package com.storm.score.exception;
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
  E001("001");



  @Getter
  private String code;
}
