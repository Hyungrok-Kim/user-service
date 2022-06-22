package per.khr.main.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import per.khr.main.userservice.dto.UserDto;

/**
 * interface는 기본적으로 public abstract를 내포하고 있음 -> 생략 가능.
 */
public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
}
