package richlife.user.RichLife_User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import richlife.user.RichLife_User.entities.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findUserInfoById(Integer id);

    Optional<UserInfo> findByPhone(String phone);
}
