package richlife.user.RichLife_User.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDTO implements Serializable {
    private String phone;
    private String gender;
    private Date birthday;
    private String avatar;
}
