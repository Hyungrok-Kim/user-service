package per.khr.main.userservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @EnableWebSecurity
 * 해당 Configuration 클래스를 Spring Security Config 클래스로 사용하겠다.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Cross-Site Request Forgery 방지

        http.authorizeRequests().antMatchers("/users/**").permitAll();
        // /users/* vs /users/**
        // ex)
        // 전자는 /users/123 정도 까지만
        // 후자는 /users/123/123이더라도 / 하위까지 다 포함해서 설정하겠다.

        http.headers().frameOptions().disable(); // h2-console 접근을 막지 않는 설정
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
