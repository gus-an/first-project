package com.gus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 프로젝트의 메인 클래스 : 항상 프로젝트 최상단
@SpringBootApplication // 스프링부트의 자동설정, 스프링 Bean 읽기와 생성 모두 자동 설정
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // 내장 WAS 실행 : 스프링부트로 만들어진 Jar 파일 실행
    }
    // 실행하면, 톰캣이 8080포트로 실행됨.
    // http://localhost:8080/h2-console
    // http://localhost:8080/api/v1/posts/1
}
