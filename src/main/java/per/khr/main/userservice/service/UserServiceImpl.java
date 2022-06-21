package per.khr.main.userservice.service;

import org.springframework.stereotype.Service;
import per.khr.main.userservice.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

    /**
     * userDto -> userEntity 객체로 변환 후 dao에 insert
     *
     * @param userDto : userEntity 객체로 변환 후 dao로 전달할 userDto
     * @return
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        return null;
    }
}
