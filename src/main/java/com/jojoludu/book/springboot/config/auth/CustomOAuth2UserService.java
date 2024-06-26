package com.jojoludu.book.springboot.config.auth;

import com.jojoludu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoludu.book.springboot.config.auth.dto.SessionUser;
import com.jojoludu.book.springboot.domain.user.User;
import com.jojoludu.book.springboot.domain.user.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 구글 로그인 이후 가져온 사용자의 정보(email,name, picture 등)들을 기반으로
 * 가입 및 정보수정 세션 저장 등의 기능을 지원
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //1.getRegistrationId
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 지금은 구글만 사용해 불필요하지만, 추후 네이버 로그인 연동 시에 네이버인지 구글인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //2. getUserNameAttributeName
        // - OAuth2로그인 진행 시 키가 되는 필드값(Primary Key와 같은 의미라고 생각하면됨)
        // - 구글: 기본적으로 코드를 지원함(코드 "sub")
        // - 네이버,카톡 : 기본 지원하지 않음
        // - 이후 네이버 로그인과 구글 로그인을 동시에 지원할 때 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();

        //3.OAuthAttributes
        // - OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        // - 이후에도 네이버 등 다른 소셜로그인도 이 클래스를 사용
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
            oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //4.SessionUser
        // - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
        // - 왜 User클래스를 사용하지 않고 새로 만들어서 쓰는지는 이후 설명
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
            attributes.getAttributes(),
            attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
            .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
            .orElse(attributes.toEntity());

        return userRepository.save(user);

    }
}
