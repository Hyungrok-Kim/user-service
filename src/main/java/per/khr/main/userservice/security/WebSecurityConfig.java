package per.khr.main.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import per.khr.main.userservice.service.UserService;

/**
 * @EnableWebSecurity 해당 Configuration 클래스를 Spring Security Config 클래스로 사용하겠다.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService service;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(UserService service, BCryptPasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * 정의해 둔 AuthenticationFilter 객체에 Security Manager를 등록 후 리턴.
     * 관리를 Security쪽에서 해주게 하기 위한 설정.
     *
     * @return
     * @throws Exception
     */
    private AuthenticationFilter getAuthFilter() throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(service, authenticationManager());

        return authFilter;
    }
    /**
     * 접근 권한 설정.
     *
     * .addFilter(getAuthFilter())
     * .addFilterBefore(getAuthFilter(), UsernamePasswordAuthenticationFilter.class)
     * 은 동일한 기능을 수행한다.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Cross-Site Request Forgery 방지 설정
        http.httpBasic().disable(); // Spring security basic login form 사용하지 않겠다는 설정

//        http.authorizeRequests().antMatchers("/users/**").permitAll();
        // /users/* vs /users/**
        // ex)
        // 전자는 /users/123 정도 까지만
        // 후자는 /users/123/123이더라도 / 하위까지 다 포함해서 설정하겠다.

        http
                .authorizeRequests()
                .antMatchers("/users").permitAll()
//                    .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .and()
                .addFilter(getAuthFilter());
//                .addFilterBefore(getAuthFilter(), UsernamePasswordAuthenticationFilter.class);


        http.formLogin()
                .loginPage("/users/login")
                .usernameParameter("email");
        // /users 요청 시 AuthenticationFilter를 거치게끔 설정

//        http
//                .authorizeRequests().antMatchers("/users/**").hasIpAddress("localhost")
//                .and()
//                .addFilter(getAuthFilter());

        http.headers().frameOptions().disable(); // h2-console 접근을 막지 않는 설정
    }
    /**
     * AuthenticationManagerBuilder의 userDetailsService로 등록하기 위해서
     * UserService 객체에서 extends로 UserDeatilsService를 상속받아주자~
     * <p>
     * AuthenticationManagerBuilder가 service 등록한 userDatilsService의 loadByUsername 메소드를
     * 거친 후 return 받은 User객체와 입력한 ReqeustLogin 유저 정보 중 패스워드를 BCryptPasswordEncoder로 암호화 한 후 비교한다~
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(passwordEncoder);
    }
}
