package com.exemplo.backend.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exemplo.backend.auth.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        List<String> roles = new ArrayList<>();

        // Verifica se tem Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
                roles = jwtUtil.getRolesFromToken(token);
                System.out.println("[JwtFilter] Token recebido: " + token);
                System.out.println("[JwtFilter] Username extraído: " + username);
                System.out.println("[JwtFilter] Roles extraídas: " + roles);
            } catch (Exception e) {
                System.out.println("[JwtFilter] Erro ao extrair dados do token: " + e.getMessage());
                username = null;
            }
        }

        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth != null) {
            System.out.println("[JwtFilter] Auth no contexto: " + existingAuth);
            System.out.println("[JwtFilter] Authorities no contexto: " + existingAuth.getAuthorities());
        } else {
            System.out.println("[JwtFilter] Auth no contexto: null");
        }

        // Se token e username extraídos, segue
        if (username != null && existingAuth == null) {
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(username, null, authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("[JwtFilter] Autenticação registrada no contexto: " + authentication);
        }

        chain.doFilter(request, response);
    }

}
