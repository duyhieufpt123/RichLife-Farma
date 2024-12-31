package richlife.user.RichLife_User.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import richlife.user.RichLife_User.constants.Role;
import richlife.user.RichLife_User.dto.response.UserInfoResponseDTO;
import richlife.user.RichLife_User.dto.response.UserLoginResponseDTO;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private UserLoginResponseDTO user;
    private UserInfoResponseDTO userInfo;

    @Data
    public static class UserLoginResponseDTO implements Serializable {
        private Integer id;
        private String email;
        private Role role;
    }
}
