package com.gus.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어느 컨트롤러든지 @LoginUser 만 사용하면 세션 정보 가져올 수 있음 (WebConfig와 Resolver 등록 필요)
// @LoginUser 어노테이션 생성
@Target(ElementType.PARAMETER) // 이 어노테이션이 생성될 수 있는 위치 : 메소드의 파라미터로 선언된 객체
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
