package per.khr.main.userservice.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.khr.main.userservice.dao.UserDao;
import per.khr.main.userservice.dao.UserEntity;
import per.khr.main.userservice.dto.UserDto;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * userDto -> userEntity 객체로 변환 후 dao에 insert
     *
     * @param userDto : userEntity 객체로 변환 후 dao로 전달할 userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        // UUID로 userId UNIQUE하게 세팅
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPassword("encryted password");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        // UserDao 클래스에서 CrudRepository 클래스를 상속받았기 때문에 save메소드가 자동으로 구현되어있다.
        userDao.save(userEntity);
        return userDto;
    }
}
