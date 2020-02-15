package com.gus.springboot.web;

import com.gus.springboot.service.posts.PostsService;
import com.gus.springboot.web.dto.PostsResponseDto;
import com.gus.springboot.web.dto.PostsSaveRequestDto;
import com.gus.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 컨트롤러는 결괏값으로 여러 테이블을 조인해서 줘야할 경우 빈번함.
@RequiredArgsConstructor
@RestController // html -> 컨트롤러.save(Dto) -> 서비스.save(Dto) -> Repository.save(Dto.toEntity())
public class PostsApiController {

    private final PostsService postsService; // @Autowired 대신 생성자로 주입 받는 방식

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) { // Param 처럼 Body 요구
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete (@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
