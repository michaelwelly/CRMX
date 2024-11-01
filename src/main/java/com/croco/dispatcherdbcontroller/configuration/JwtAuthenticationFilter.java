package com.croco.dispatcherdbcontroller.configuration;

import com.croco.dispatcherdbcontroller.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    private final String SECRET_KEY = "53A73E5F1C4E0A2D3B5F2D784E6A1B423D6F247D1F6E5C3A596D635A75327855";

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
// Проверка наличия префикса Bearer
        if (authorizationHeader != null) {
            // Проверка наличия префикса Bearer
            if (authorizationHeader.startsWith(BEARER_PREFIX)) {
                jwt = authorizationHeader.substring(BEARER_PREFIX.length());
            } else {
                jwt = authorizationHeader; // Если префикса нет, оставляем как есть
            }
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                username = claims.get("sub", String.class); // Извлечение имени пользователя
                String role = claims.get("role", String.class); // Извлечение роли
                Long userId = claims.get("id", Long.class); // Извлечение id

                // Проверка на истечение токена
                if (claims.getExpiration().before(new Date())) {
                    throw new SignatureException("Token has expired");
                }

                List<SimpleGrantedAuthority> authorityList = Collections.singletonList(new SimpleGrantedAuthority(role));
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    CustomUserDetails userDetails = new CustomUserDetails(userId, username, jwt, authorityList);

                    // Создаем объект аутентификации
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Устанавливаем аутентификацию в контекст безопасности
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (SignatureException e) {
                // Обработка исключения: неверная подпись токена
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (Exception e) {
                // Обработка других исключений
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return; // Прерываем выполнение, чтобы не продолжать фильтрацию
            }
        }

        try{
            chain.doFilter(request, response);
        }catch(Exception e){
            log.info("Error when proccessing chain filter");
            return;
        }

    }

    public  Long getUserIdFromToken(String token) {
        // Проверка наличия префикса Bearer
        String jwt = null;
        if (token.startsWith(BEARER_PREFIX)) {
            jwt = token.substring(BEARER_PREFIX.length());
        } else {
            jwt = token; // Если префикса нет, оставляем как есть
        }
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims.get("id", Long.class); // Извлечение id
    }

}