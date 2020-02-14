package com.gus.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column; // 'spring-boot-starter-data-jpa 에 들어있음
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter // 코틀린 등 새 언어 전환으로 롬복이 더이상 필요없을 경우 쉽게 지우기 위해 멀리둠
@NoArgsConstructor // public Posts() {}
@Entity // 테이블과 링크될 클래스임
public class Posts {
    @Id // 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성규칙, auto-increment 적용
    private Long id; // bigint

    @Column(length = 500, nullable = false) // 기본 VARCHAR(255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)  // TEXT
    private String content;

    private String author;

    @Builder // 필드값 명확하게 생성가능
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
