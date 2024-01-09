package com.storm.score.com.security;
/**
 *
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * description    :
 * packageName    : com.storm.score.com.security
 * fileName       : JwtAccessDeniedHandler
 * author         : wammelier
 * date           : 2024/01/08
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/01/08        wammelier       최초 생성
 */

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }
}
