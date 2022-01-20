package xlinek.mendelu.xlinek.project_02.user;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    User findUserById(int id);
    User findUserByLogin(String login);
}
