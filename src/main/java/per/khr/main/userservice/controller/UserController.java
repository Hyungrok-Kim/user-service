package per.khr.main.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import per.khr.main.userservice.dto.UserDto;
import per.khr.main.userservice.service.UserService;
import per.khr.main.userservice.vo.RequestLogin;
import per.khr.main.userservice.vo.RequestUser;
import per.khr.main.userservice.vo.ResponseUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Environment env;
    private UserService service;

    @Autowired
    public UserController(Environment env, UserService service) {
        this.env = env;
        this.service = service;
    }

    @GetMapping("/health_check")
    public String status() {
        log.info("[커넥션 체크]");

        return String.format(
                "user-service connected"
                        + ", port(server.port) : " + env.getProperty("server.port") // application.yaml 파일의 server.port key 값
                        + " " + env.getProperty("greeting.name")
        );
    }

    /**
     * 사용자 목록 조회.
     *
     * @return
     */
    @GetMapping("/users")
    public List<ResponseUser> getUsers() {
        return null;
    }

    /**
     * 사용자 가입.
     *
     * @param user : 사용자 정보.
     * @return ResponseEntity<ResponseUser ≥ : 생성한 사용자 정보.
     */
    @PostMapping("/")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        // RequestUser -> userDto 객체 변환을 안전하게 하기 위한 ModelMapper 객체
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Matching을 STRICT하게 설정

        // Request로부터 전달된 RequestUser 객체를 ModelMapper 객체를 이용해 UserDto객체로 변환
        // RequestUser -> userDto
        UserDto userDto = mapper.map(user, UserDto.class);

        // UserService 호출
        userDto = service.createUser(userDto);

        // userDto -> ResponseUser
        ResponseUser resultUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(resultUser);
    }

    /**
     * 유저 정보 조회.
     *
     * @param userId : 사용자 아이디.
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseUser getUser(@PathVariable("userId") String userId) {
        return null;
    }

    /**
     * 유저 정보 수정.
     *
     * @param user : 사용자 정보.
     * @return
     */
    @PutMapping("/{userId}")      // PutMapping에 지정한 key 값
    public String modifyUser(@PathVariable("userId") RequestUser user) {
        return "modify success";
    }

    /**
     * 유저 정보 삭제(탈퇴).
     *
     * @param user : 사용자 정보.
     * @return
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") RequestUser user) {
        return "delete success";
    }

    /**
     * 로그인 모놀리틱 방식(옛날 방식).
     */
//    @PostMapping("/login")
//    public ResponseEntity<ResponseUser> loginCheck(@RequestBody RequestLogin user, HttpServletRequest request) {
//          모놀리틱 방식(예전 방식)
////        HttpSession session = request.getSession(false); // false로 설정하면 null이면 null return, session있으면 해당 session return
//
//    }
}
