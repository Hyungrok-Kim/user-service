package per.khr.main.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "Email cannot be null") // NotNull일 경우 메시지
    @Email                                   // Email형식 체크
    private String email;
    //  실제로 패스워드 밸리데이션 체크는 프론트에서 하는 게 아니라 서버에서 하는 것이 좋다.
    //  Proxy 툴을 이용해 변조해서 서버쪽으로 넘길 수 있는 경우가 있기 때문에 입력값 검증은 서버에서 하는 걸 지향
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be equal or grater than 8 character")
    private String password;
}
