package com.eweb.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eweb.dao.AdminRepository;
import com.eweb.dao.RoleRepository;
import com.eweb.model.Admin;
import com.eweb.model.Role;
import com.eweb.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
   private  RoleRepository roleRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
throws ServletException, IOException {

try {
        String jwt = parseJwt(request);
        if (jwt != null && jwtUtil.isTokenValid(jwt)) {
            String email = jwtUtil.extractEmail(jwt);

            Optional<Admin> adminOpt = adminRepository.findByEmail(email);
            if (adminOpt.isPresent()) {
                Admin admin = adminOpt.get();
                Optional<Role> byId = roleRepository.findById(admin.getRoleId());
                User userDetails = new org.springframework.security.core.userdetails.User(
                        admin.getEmail(), 
                        admin.getPassword(), 
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" +byId.get().getName()))
                );

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, 
                                null, 
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
     }catch (Exception e) {
	logger.error("JWT error: {}", e.getMessage());
	
	  SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }
}
