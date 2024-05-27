package com.jojoludu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    //@SpringBootApplication으로 스브링 부트 자동 설정, 스프링 Bean읽기 생성 자동
    //여기 부터 설정을 읽어감 -> 항상 프로젝트 최 상단에 위치해야함
    public static void main(String[] args) {
        //SpringApplication.run으로 내장 WAS 실행
        //내장 WAS를 사용하는 것을 권장 -> 언제나 같은 환경에서 스프링 부트를 배포할 수 있어서
        SpringApplication.run(Application.class, args);
    }
}
