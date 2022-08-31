package per.khr.main.userservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import per.khr.main.userservice.dao.UserDao;
import per.khr.main.userservice.dao.UserEntity;
import per.khr.main.userservice.dto.UserDto;
import per.khr.main.userservice.vo.RequestUser;
import per.khr.main.userservice.vo.ResponseUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * UserSerivce 인터페이스 구현체가 여러개 있다면 @Service("UserSerivce")와 같이 식별자 추가하고
 * Controller에서 Autowired 대신 @Qualifier 어노테이션을 사용하는 편.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private BCryptPasswordEncoder passwordEncoder;
    static final long EXPIRATIONTIME = 1000 * 60;
    static final String SIGNINGKEY = "signingKey";
    static final String BEARER_PREFIX = "Bearer";

    @Autowired
    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * userDto -> userEntity 객체로 변환 후 dao에 insert.
     *
     * @param userDto : userEntity 객체로 변환 후 dao로 전달할 userDto.
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        // UUID로 userId UNIQUE하게 세팅
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        // UserDao 클래스에서 CrudRepository 클래스를 상속받았기 때문에 save메소드가 자동으로 구현되어있다.
        userDao.save(userEntity);
        return userDto;
    }

    /**
     * 모든 User 조회.
     *
     * @return List<ResponseUser>
     */
    @Override
    public List<UserEntity> getUsers() {
        return userDao.findAll();
    }

    /**
     * @PathVariable을 통한 User 조회.
     *
     * @param userId : 사용자 아이디
     * @return UserEntity
     */
    @Override
    public UserEntity getUser(String userId) {
        return userDao.findByUserId(userId);
    }

    /**
     * @PathVariable로 받은 유저에 해당하는 User 정보 수정.
     * @param userDto
     * @return
     */
    @Override
    public UserDto modifyUser(UserDto userDto) {
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        userDao.save(userEntity);
        return userDto;
    }

    /**
     * @PathVariable로 받은 유저에 해당하는 User 삭제.
     * @param userId
     * @return
     */
    @Override
    public UserDto deleteUser(String userId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity deletedUser = userDao.deleteByUserId(userId);

        UserDto userDto = mapper.map(deletedUser, UserDto.class);

        return userDto;
    }

    /**
     * UserService에서 상속받은 UserDetailsService의 구현 메소드.
     * Spring security에서 User 객체는 제공해줍니다~
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userDao.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                true, true, true, true,
                Collections.emptyList()
        );
    }

    static public void addJWTToken(HttpServletRequest request, HttpServletResponse response, Authentication auth) {
        String email = ((User) auth.getPrincipal()).getUsername();

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
