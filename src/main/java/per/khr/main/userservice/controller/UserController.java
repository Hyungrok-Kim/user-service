package per.khr.main.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import per.khr.main.userservice.vo.RequestUser;
import per.khr.main.userservice.vo.ResponseUser;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private Environment env;

    @Autowired
    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
        log.debug("커넥션 체크");

        return String.format(
                "user-service connected"
                + ", port(server.port) : " + env.getProperty("server.port") // application.yaml 파일의 server.port key 값
        );
    }

    /**
     * 사용자 목록 조회
     *
     * @return
     */
    @GetMapping("/users")
    public List<ResponseUser> getUsers() {
        return null;
    }

    /**
     * 사용자 가입
     *
     * @param user : 사용자정보
     * @return
     */
    @PostMapping("/")
    public String createUser(@RequestBody RequestUser user) {
        return "created success";
    }

    /**
     * 유저 정보 조회
     *
     * @param userId : 사용자 아이디
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseUser getUser(@PathVariable("userId") String userId) {
        return null;
    }

    /**
     * 유저 정보 수정
     *
     * @param user : 사용자 정보
     * @return
     */
    @PutMapping("/{userId}")      // PutMapping에 지정한 key 값
    public String modifyUser(@PathVariable("userId") RequestUser user) {
        return "modify success";
    }

    /**
     * 유저 정보 삭제(탈퇴)
     *
     * @param user : 사용자 정보
     * @return
     */
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") RequestUser user) {
        return "delete success";
    }
}
