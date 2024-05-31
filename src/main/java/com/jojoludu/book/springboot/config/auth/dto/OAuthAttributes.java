package com.jojoludu.book.springboot.config.auth.dto;

import com.jojoludu.book.springboot.domain.user.Role;
import com.jojoludu.book.springboot.domain.user.User;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 *
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name,
        String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    //1.
    /**
     * OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값을 한하나를 변환해야 함
     * */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
        Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
            .name(String.valueOf(response.get("name")))
            .email(String.valueOf(response.get("email")))
            .picture(String.valueOf(response.get("profile_image")))
            .attributes(response)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
        Map<String, Object> attributes) {
        return OAuthAttributes.builder()
            .name((String) attributes.get("name"))
            .email(String.valueOf(attributes.get("email")))
            .picture(String.valueOf(attributes.get("picture")))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
    }

    /**
     * User 엔티티 생성
     * OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때
     * 가입할 때의 기본 권한을 GUEST로 주기 해 role 빌더값에 Role.GUEST사용
     * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionUser 클래스 생성
     * */
    public User toEntity() {
        return User.builder()
            .name(name)
            .email(email)
            .picture(picture)
            .role(Role.GUEST)
            .build();
    }

}
