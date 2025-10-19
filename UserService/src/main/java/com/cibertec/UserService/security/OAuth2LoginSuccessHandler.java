package com.cibertec.UserService.security;

import com.cibertec.UserService.model.User;
import com.cibertec.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Usar el email del usuario para generar el token
            String token = tokenProvider.generateToken(user.getEmail());
            
            // Redirigir al frontend con el token
            String redirectUrl = String.format("http://localhost:3000/oauth2/redirect?token=%s", token);
            getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
