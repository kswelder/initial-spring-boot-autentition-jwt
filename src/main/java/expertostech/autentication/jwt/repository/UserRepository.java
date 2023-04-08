package expertostech.autentication.jwt.repository;

import expertostech.autentication.jwt.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    public Optional<UserModel> findByLogin(String login);

}
