package per.khr.main.userservice.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * extends CrudRepository
 * -> springframework.data.jsp랑 JPA랑 mapping해주는 클래스
 * -> UserDao를 JPA의 라이프사이클에 맞춰 관리하겠다.
 */
public interface UserDao extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email); // CrudRepository를 상속받았으니 JPA에서 where절에 email을 넣어서 쿼리 자동 생성해줍니다~
    List<UserEntity> findAll();
    UserEntity findByUserId(String userId);
}
