package com.gus.springboot.config.auth.dto;

import com.gus.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// 세션에 사용자 정보를 저장하기 위한 Dto
// User 클래스를 직접 사용하지 않는 이유? 엔티티 클래스에는 직렬화가 구현 X, 구현하면 다른 엔티티와의 관계 형성 -> 성능 저하
// 인증된 사용자 정보 외에는 필요가 없음.
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
