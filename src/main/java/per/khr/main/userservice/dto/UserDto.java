package per.khr.main.userservice.dto;

import lombok.*;

import java.util.Date;

//@Data // Setter, Getter를 자동으로 만들기 위한 어노테이션 (사용 지양 -> Setter는 커스텀하는 경우가 많기 때문)
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String userId;
    private Date createdAt;

    private String encrytedPassword;
    private String decrytedPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public String getDecrytedPassword() {
        return decrytedPassword;
    }

    public void setDecrytedPassword(String decrytedPassword) {
        this.decrytedPassword = decrytedPassword;
    }
}
