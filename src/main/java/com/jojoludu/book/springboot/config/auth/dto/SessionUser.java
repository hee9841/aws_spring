package com.jojoludu.book.springboot.config.auth.dto;


import com.jojoludu.book.springboot.domain.user.User;
import java.io.Serializable;
import lombok.Getter;

/**
 * 인증된 사용자 정보만 필요함(name, email, picture만 필드로 선언) 그 외에 필요한 정들은 없음
 *
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
