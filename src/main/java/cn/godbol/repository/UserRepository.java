package cn.godbol.repository;

import cn.godbol.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Li on 2016/10/15.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User getByUsername(String username);
    User getById(Long id);
}
