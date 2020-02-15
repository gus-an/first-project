package com.gus.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 이를 상속할 경우, 칼럼을 인식
@EntityListeners(AuditingEntityListener.class) // 클래스에 Auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate // Entity 가 생성될 때 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
