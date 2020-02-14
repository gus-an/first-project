package com.gus.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // final 이 있는 필드를 포함하여 생성자 생성
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
