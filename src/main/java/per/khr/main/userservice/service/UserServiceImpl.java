package per.khr.main.userservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import per.khr.main.userservice.dao.UserDao;
import per.khr.main.userservice.dao.UserEntity;
import per.khr.main.userservice.dto.UserDto;

import java.util.Collections;
import java.util.UUID;

/**
 * UserSerivce 인터페이스 구현체가 여러개 있다면 @Service("UserSerivce")와 같이 식별자 추가하고
 * Controller에서 Autowired 대신 @Qualifier 어노테이션을 사용하는 편.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private BCryptPasswordEncoder passwordEncoder;

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
}
