package com.gus.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Domain : 포스트 개체, 포스트 DB 개체 등, + 비즈니스 처리

// DB layer 접근자 (Dao), <Entity 클래스, PK 타입>, 기본 CRUD 자동 생성
public interface PostsRepository extends JpaRepository<Posts, Long>{
}
