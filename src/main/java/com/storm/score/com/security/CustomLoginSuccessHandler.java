//package com.storm.score.com.security;
///**
// *
// */
//
//import java.io.IOException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//
///**
// * description    :
// * packageName    : com.storm.score.com.security
// * fileName       : CustomLoginSuccessHandler
// * author         : wammelier
// * date           : 2023/12/26
// * ===========================================================
// * DATE              AUTHOR             NOTE
// * -----------------------------------------------------------
// * 2023/12/26        wammelier       최초 생성
// */
//public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//  @Override
//  public void ouAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//
//    SecurityContextHolder.getContext().setAuthentication(authentication);
////    response.sendRedirect("/");
//
//  }
//
//}
