package richlife.user.RichLife_User.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import richlife.user.RichLife_User.constants.Gender;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "[user_info]")
public class UserInfo {
    @Id
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "dob")
    private Date birthday;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
}
