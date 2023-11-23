package com.storm.score.vo;
/**
 *
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description    :
 * packageName    : com.storm.score.vo
 * fileName       : User
 * author         : wammelier
 * date           : 2023/11/23
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/11/23        wammelier       최초 생성
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  String Id;
  String Name;
  String pwd;
  String email;
  String group;

}
