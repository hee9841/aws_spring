package com.jojoludu.book.springboot.web;

import com.jojoludu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RestController는 json 타입을 반환하는 컨트롤러
//@Controller 차이는 @ResponseBody가 추가
public class HelloController {

    //HTTP Method Get 요청 받는 API를 만들어줌
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
        @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
