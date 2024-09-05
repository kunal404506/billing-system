package com.csi.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userEmail = null;

        //Checking and Format
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                userEmail = jwtUtil.extractUsername(token);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to get JWT token", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                log.warn("JWT Token Has Expired", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "JWT Token Has Expired");
            } catch (MalformedJwtException e) {
                log.warn("Invalid JWT Token", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid JWT Token");
            } catch (SignatureException e) {
                log.warn("JWT signature does not match locally computed signature", e.getCause());
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid JWT signature");
            } catch (Exception e) {
                log.warn("Server Error", e.getCause());
                e.printStackTrace();
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
            }
        }

        //Security
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                log.warn("Invalid JWT Token");
                response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid JWT Token");
            }
        }
        filterChain.doFilter(request, response);
    }
}