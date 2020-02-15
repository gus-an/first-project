package com.gus.springboot.service.posts;

import com.gus.springboot.domain.posts.Posts;
import com.gus.springboot.domain.posts.PostsRepository;
import com.gus.springboot.web.dto.PostsListResponseDto;
import com.gus.springboot.web.dto.PostsResponseDto;
import com.gus.springboot.web.dto.PostsSaveRequestDto;
import com.gus.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service // 서비스 메소드는 트랜잭션과 도메인 간의 순서만 보장
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        entity.update(requestDto.getTitle(), requestDto.getContent());
        // JPA 의 핵심 : 엔티티가 영속성 컨텍스트에 포함되어있느냐.
        // 트랜잭션 안에서 DB에서 데이터를 가져오면, 영속성 컨텍스트 유지.
        // Update 쿼리 없이, 트랜잭션 끝나는 시점에 테이블 반경.
        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id =" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //트랜잭션 중 조회 기능만 남겨주어 조회 속도 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // 람다식 .map(posts -> new PostsListResponseDto(posts)) 와 같음
                .collect(Collectors.toList());
    }
}
