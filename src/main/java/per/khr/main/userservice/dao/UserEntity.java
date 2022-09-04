package per.khr.main.userservice.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(length = 50)
    private String name;
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;
    @Column(name = "encrypted_password", nullable = false, unique = true)
    private String encryptedPassword;
}
