package com.jojoludu.book.springboot.web;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

//1. 태스트 실행할 때 JUnit에 내장된 실행자 외 다른 실행자 실행
//여기서는 SpringRunner라는 스프링 실행자를 사용
// 스프링 부투 테스트와 jUnit사이 연결자
@ExtendWith(SpringExtension.class)
//2. 여러 스프링 테스트 중 Web(Spring MVC에 집중할 수 있는 어노테이션
//WebMvcTest 선언시 @Controller , @ControllerAdvice등을 사용할 수 있음
//@Service, Component, Repository등은 사용할 수 없음
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired//3.bean 주입
    private MockMvc mvc;
    //4.MockMvc
    //웹 API를 테스트 할 때 사용
    //스프링 MVC 테스트의 시작점
    //이 클래스를 통해 HTTP GET, POST등에 대한 API 테스트를 할 수있음

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        //5. perform(get(..))
        //MockMvc를 통해 /hello 주소로 HTTP GET 요청함
        //체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언
        //6. andExpect
        //mvc.perform의 결과 검증
        //status().isOk() 200인지 확인
        //7.응답 본문 내용 검증 : hello인지
        mvc.perform(get("/hello"))//5.
            .andExpect(status().isOk())//6.
            .andExpect(content().string(hello));//7.
    }

    @Test
    public void helloDTo_리턴() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(name)))
            .andExpect(jsonPath("$.amount", is(amount)));

        //jsonPath은 JSON응답값을 필드별로 검증할 수 있는 메소드
        //$를 기준으로 필드명 명시
    }

}
