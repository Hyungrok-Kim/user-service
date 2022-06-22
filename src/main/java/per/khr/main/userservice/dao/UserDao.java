package per.khr.main.userservice.dao;

import org.springframework.data.repository.CrudRepository;

/**
 * extends CrudRepository
 * -> springframework.data.jsp랑 JPA랑 mapping해주는 클래스
 * -> UserDao를 JPA의 라이프사이클에 맞춰 관리하겠다.
 */
public interface UserDao extends CrudRepository<UserEntity, Long> {
//    List<UserEntity> findByName(String name);
}
