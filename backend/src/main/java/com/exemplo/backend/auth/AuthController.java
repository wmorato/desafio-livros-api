package com.exemplo.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import com.exemplo.backend.dto.UserDTO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.stream.Collectors;
import com.exemplo.backend.dto.UserDTO;
import com.exemplo.backend.auth.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        // Pegue username e roles
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Gere o token JWT
        String token = jwtUtil.generateToken(username, roles);

        // Monte o DTO do usuário
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRoles(roles);

        // Monte a resposta
        AuthResponse resp = new AuthResponse();
        resp.setToken(token);
        resp.setUser(userDTO);

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/leitor")
    public ResponseEntity<?> loginLeitor() {
        String token = jwtUtil.generateToken("leitor", List.of("ROLE_LEITOR"));
        return ResponseEntity.ok(Map.of("token", token));
    }

}
