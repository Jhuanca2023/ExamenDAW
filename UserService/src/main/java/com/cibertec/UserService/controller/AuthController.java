package com.cibertec.UserService.controller;

import com.cibertec.UserService.model.User;
import com.cibertec.UserService.repository.UserRepository;
import com.cibertec.UserService.security.JwtTokenProvider;
import com.cibertec.UserService.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login-success")
    public ResponseEntity<?> loginSuccess(Authentication authentication, @RequestParam(required = false) String token) {
        // Si viene token del parámetro (desde OAuth2LoginSuccessHandler)
        if (token != null && !token.isEmpty()) {
            try {
                if (jwtTokenProvider.validateToken(token)) {
                    String email = jwtTokenProvider.getEmailFromJWT(token);
                    Optional<User> user = userRepository.findByEmail(email);
                    
                    if (user.isPresent()) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Login exitoso");
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("id", user.get().getId());
                        userInfo.put("name", user.get().getName());
                        userInfo.put("email", user.get().getEmail());
                        userInfo.put("imageUrl", user.get().getImageUrl());
                        response.put("user", userInfo);
                        response.put("token", token);
                        response.put("tokenType", "Bearer");
                        
                        return ResponseEntity.ok(response);
                    }
                }
            } catch (Exception e) {
                // Si hay error con el token, continuar con el flujo normal
            }
        }
        
        // Flujo normal de autenticación
        if (authentication != null && authentication.isAuthenticated()) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            
            // Generar token JWT
            String newToken = jwtTokenProvider.generateToken(userPrincipal.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login exitoso");
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", userPrincipal.getId());
            userInfo.put("name", userPrincipal.getName());
            userInfo.put("email", userPrincipal.getEmail());
            userInfo.put("imageUrl", userPrincipal.getImageUrl());
            response.put("user", userInfo);
            response.put("token", newToken);
            response.put("tokenType", "Bearer");
            
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.badRequest().body("Error en la autenticación");
    }

    @GetMapping("/login-failure")
    public ResponseEntity<?> loginFailure() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Error en el login");
        response.put("error", "No se pudo autenticar con Google");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Remover "Bearer " del token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            if (jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmailFromJWT(token);
                Optional<User> user = userRepository.findByEmail(email);
                
                if (user.isPresent()) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("valid", true);
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", user.get().getId());
                    userInfo.put("name", user.get().getName());
                    userInfo.put("email", user.get().getEmail());
                    userInfo.put("imageUrl", user.get().getImageUrl());
                    response.put("user", userInfo);
                    return ResponseEntity.ok(response);
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", "Token inválido");
            return ResponseEntity.badRequest().body(response);
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            response.put("message", "Error al validar el token");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
