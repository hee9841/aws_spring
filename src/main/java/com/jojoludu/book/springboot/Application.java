package com.jojoludu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;


@SpringBootApplication
public class Application {

    //@SpringBootApplication으로 스브링 부트 자동 설정, 스프링 Bean읽기 생성 자동
    //여기 부터 설정을 읽어감 -> 항상 프로젝트 최 상단에 위치해야함
    public static void main(String[] args) {
        //SpringApplication.run으로 내장 WAS 실행
        //내장 WAS를 사용하는 것을 권장 -> 언제나 같은 환경에서 스프링 부트를 배포할 수 있어서
        try {
            System.out.println("애플리케이션이 시작되기 전에 10분 동안 대기합니다...");
            Thread.sleep(600000); // 10분 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 인터럽트 상태 복구
            System.err.println("대기 중 인터럽트 발생: " + e.getMessage());
        }

        //설정한 값을 pid commend로 사용하기 위해 main을 수정
        SpringApplication application = new SpringApplication(Application.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
