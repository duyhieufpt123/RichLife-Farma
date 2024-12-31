package richlife.user.RichLife_User.repository;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import org.springframework.data.jpa.repository.JpaRepository;
import richlife.user.RichLife_User.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findFirstByEmail(String email);
}
