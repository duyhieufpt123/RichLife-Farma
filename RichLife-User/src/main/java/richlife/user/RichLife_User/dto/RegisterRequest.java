package richlife.user.RichLife_User.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import richlife.user.RichLife_User.constants.Gender;
import richlife.user.RichLife_User.utils.EnumPassword;
import richlife.user.RichLife_User.utils.EnumPhone;
import richlife.user.RichLife_User.utils.EnumValue;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class RegisterRequest {
    @Email(message = "Email phải đúng định dạng")
    @NotNull(message = "Email không được để trống")
    private String email;

    @EnumPassword(message = "Mật khẩu phải từ 8 đến 16 ký tự, bao gồm ít nhất 1 chữ hoa, 1 chữ thường, 1 chữ số và 1 ký tự đặc biệt!")
    @NotNull(message = "Mật khẩu không được để trống")
    private String password;

    @NotNull(message = "Số điện thoại không được để trống")
    @EnumPhone(message = "Số điện thoại từ 10 - 11 số")
    private String phone;

    @NotNull(message = "Giới tính không thể để trống")
    @EnumValue(name = "type", enumClass = Gender.class, message = "Giới tính phải đúng format")
    private String gender;

    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @PastOrPresent(message = "Ngày sinh không được vượt ngày hiện tại")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Ngày sinh không được để trống")
    private Date birthday;


}
