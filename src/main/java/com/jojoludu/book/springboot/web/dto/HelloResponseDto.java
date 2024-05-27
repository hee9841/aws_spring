package com.jojoludu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//1. Getter : get메소드 생성
@Getter
//2. 선언된 모든 final필드가 포함된 생성자를 생성해줌
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
