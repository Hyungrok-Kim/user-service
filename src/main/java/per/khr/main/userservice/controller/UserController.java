package per.khr.main.userservice.controller;

import org.springframework.web.bind.annotation.*;
import per.khr.main.userservice.vo.RequestUser;
import per.khr.main.userservice.vo.ResponseUser;

import java.util.List;

@RestController
public class UserController {
    @GetMapping("/users")
    public List<ResponseUser> getUsers() {

        return null;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        return "created success";
    }

    @PutMapping("/users/{userId}")      // PutMapping에 지정한 key 값
    public String modifyUser(@PathVariable("userId") RequestUser user) {
        return "modify success";
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable("userId") RequestUser user) {
        return "delete success";
    }
}
