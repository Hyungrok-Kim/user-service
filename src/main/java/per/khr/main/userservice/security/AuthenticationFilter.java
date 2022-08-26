package per.khr.main.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import per.khr.main.userservice.service.UserService;
import per.khr.main.userservice.vo.RequestLogin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * Spring security을 활용한 로그인을 위한 Filter 구현해보자~
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    static final long EXPIRATIONTIME = 1000 * 60;
    static final String SIGNINGKEY = "signingKey";
    static final String BEARER_PREFIX = "Bearer";
    private UserService service;

    @Autowired
    public AuthenticationFilter(UserService service, AuthenticationManager authManager) {
        super.setAuthenticationManager(authManager);
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/users/login","POST"));
        this.service = service;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    creds.getEmail(),
                    creds.getPassword(),
                    Collections.emptyList()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * attemptAuthentication 메소드에서 정상적으로 return 시 실행
     * 인증 성공 시 JWT token 발행해주자~
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((User) authResult.getPrincipal()).getUsername();

        String jwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + (EXPIRATIONTIME * 30)))
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
                .compact();

        response.addHeader("Authorization", BEARER_PREFIX + " " + jwtToken);
        response.addHeader("email", email);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }
}
