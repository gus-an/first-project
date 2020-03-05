package com.gus.springboot.config.auth;

import com.gus.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    // URL 별 권한 관리 설정하는 옵션의 시작점 (이게 있어야 antMatchers 사용가능)
                    .authorizeRequests()
                    // 권한 관리 대상을 지정하는 옵션, URL HTTP 메소드별 가능, "/api/v1/**" 는 USER 권한 가진 사람만
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // 설정된 값들 이외의 URL들, 모두 인증된 사용자들에게만 허용 (로그인된)
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    // OAuth2 로그인 기능에 대한 설정 진입점
                    .oauth2Login()
                        // 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                        .userInfoEndpoint()
                            // 소셜 로그인 성공 시 후속 조치를 진핼할 UserService 인터페이스 구현체 등록
                            // 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
                            .userService(customOAuth2UserService);
    }
}
