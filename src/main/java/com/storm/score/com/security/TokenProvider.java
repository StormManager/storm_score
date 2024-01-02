package com.storm.score.com.security;
/**
 *
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * description    :
 * packageName    : com.storm.score.com.security
 * fileName       : TokenProvider
 * author         : wammelier
 * date           : 2024/01/02
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/01/02        wammelier       최초 생성
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

  private static final String AUTHORITIES_KEY = "auth";

  private final String secret;
  private final long tokenValidityInMilliseconds;
  private Key key;


  public TokenProvider(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.token_validity-in-seconds}") long tokenValidityInMilliseconds
  ) {
    this.secret = secret;
    this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
  }

  @Override
  public void afterPropertiesSet() {

    byte[] keyBytes = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // Authentication 객체의 권한 정보를 이용해서 토큰을 생성
  public String createToken(Authentication authentication) {

    // authorities 설정
    String authorities = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).collect(
            Collectors.joining(","));

    // 토큰 만료 시간 설정
    long now = (new Date()).getTime();
    Date validity = new Date(now + this.tokenValidityInMilliseconds);

    return Jwts.builder().setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact();
  }


  // 토큰에 담겨있는 정보를 이용해 Authentication 객체 리턴
  public Authentication getAuthentication(String token) {

    // 토큰을 이용하여 claim 생성
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    Collection<? extends GrantedAuthority> authorities = Arrays
        .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    // claim과 autorities 이용하여 User 객체 생성
    User principal = new User(claims.getSubject(), "", authorities);

    // 최종적으로 Authentication 객체 리턴
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  // 토큰의 유효성 검증 수행
  public boolean validateToken(String token) {

    //토큰 파싱 후 발생하는 예외 캐치 문제있으면 false, 정상이면 true 리턴
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("잘못된 JWT 토큰 서명");
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰");
    } catch (IllegalArgumentException e) {
      log.info("잘못된 JWT 토큰");
    }
    return false;
  }
}
