package per.khr.main.userservice.dto;

import lombok.*;

import java.util.Date;

@Data // Setter, Getter를 자동으로 만들기 위한 어노테이션 (사용 지양 -> Setter는 커스텀하는 경우가 많기 때문)
public class User {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createdAt;

    private String encrytedPassword;
    private String decrytedPassword;
}
