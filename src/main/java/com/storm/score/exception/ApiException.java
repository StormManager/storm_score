package com.storm.score.exception;
/**
 *
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import lombok.Getter;

/**
 * description    :
 * packageName    : com.storm.score.exception
 * fileName       : ApiException
 * author         : wammelier
 * date           : 2023/12/05
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/05        wammelier       최초 생성
 */

@Getter
@JsonIgnoreProperties({"stackTrace", "suppressed", "localizedMessage"})
public class ApiException {
  private static final long serialVersionUID = 1L;

  private String rspCode;
  private Object[] args;

  public ApiException(final String rspCode, final Object... args) {
    this.rspCode = rspCode;
    this.args = args;
  }

}
