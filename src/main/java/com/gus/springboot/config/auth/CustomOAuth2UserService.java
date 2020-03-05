package com.gus.springboot.config.auth;

import com.gus.springboot.config.auth.dto.OAuthAttributes;
import com.gus.springboot.config.auth.dto.SessionUser;
import com.gus.springboot.domain.user.User;
import com.gus.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

// 구글 로그인 후 가져온 사용자 정보 (email, name, picture) 등을 기반으로 가입, 정보수정, 세션 저장 기능
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 로그인 진행중인 서비스를 구분하는 코드. 네이버인지 구글인지 구분.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // OAuth2 로그인 진행시 키가되는 필드값. Primary Key 같음.
        // 구글은 기본적으로 코드를 지원 (기본코드 "sub"). 네이버, 카카오는 기본 지원 X
        // 네이버와 구글 로그인 동시 지원시 사용됨
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService 통해 가져온 attribute 담음. 네이버 등 다른 소셜 로그인도 이 클래스 사용.
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        // SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // DB 에 이메일로 유저를 등록 / 업그레이드
    private User saveOrUpdate(OAuthAttributes attributes) {
            User user = userRepository.findByEmail(attributes.getEmail())
                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                    .orElse(attributes.toEntity());

            return userRepository.save(user);
    }
}