package com.jojoludu.book.springboot.domain.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 스프링 시큐리티에서 권한 코드에 한상 "ROLE_"이 앞에 있어야함
 * 그래서 코드별 키값을 ROLE_GUEST, ROLE_USER로 지정함
 * **/
@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
