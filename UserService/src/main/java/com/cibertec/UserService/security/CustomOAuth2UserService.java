package com.cibertec.UserService.security;

import com.cibertec.UserService.model.AuthProvider;
import com.cibertec.UserService.model.User;
import com.cibertec.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        
        // Verificar si el usuario ya existe
        User user = userRepository.findByProviderAndProviderId(registrationId, oAuth2UserInfo.getId())
                .orElseGet(() -> registerNewUser(registrationId, oAuth2UserInfo));
                
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();
        user.setProvider(registrationId);
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setProviderType(AuthProvider.valueOf(registrationId));
        return userRepository.save(user);
    }
}
