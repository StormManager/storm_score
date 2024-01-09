package com.storm.score.com.security;
/**
 *
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * description    :
 * packageName    : com.storm.score.com.security
 * fileName       : JwtAuthenticationEntryPoint
 * author         : wammelier
 * date           : 2024/01/08
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/01/08        wammelier       최초 생성
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }

}
