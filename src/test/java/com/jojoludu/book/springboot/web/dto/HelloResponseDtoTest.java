package com.jojoludu.book.springboot.web.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;


public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        //여기서 junit의 assertThat이 아닌 assertj의 assertThat 사용
        //assertj의 장점
        //1. CoreMatchers와 달리 추가적으로 라이브러리가 필요 없음
        //      junit의 assertThat을 사용시 is()와 같은 CoreMatchers 라이브러리가 필요
        //2. 자동완성이 확실히게 지원
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
