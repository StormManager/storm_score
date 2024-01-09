package com.storm.score.vo;
/**
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.vo
 * fileName       : User
 * author         : wammelier
 * date           : 2024/01/09
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/01/09        wammelier       최초 생성
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

  private Long id;

  private String userName;
  private String password;
  private String nickName;
  private String address;
  private String phone;

}
