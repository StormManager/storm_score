package com.storm.score.com.security.vo;
/**
 *
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * description    :
 * packageName    : com.storm.score.com.security.vo
 * fileName       : JwtToken
 * author         : wammelier
 * date           : 2024/01/09
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/01/09        wammelier       최초 생성
 */

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
  private String grantType;
  private String accessToken;
  private String refreshToken;
}
