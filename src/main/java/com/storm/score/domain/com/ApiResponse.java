package com.storm.score.domain.com;
/**
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.domain.com
 * fileName       : ApiResponse
 * author         : wammelier
 * date           : 2023/12/14
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/14        wammelier       최초 생성
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse {
  private String resCode = "0000";
  private String resStatus = "Success";

}
