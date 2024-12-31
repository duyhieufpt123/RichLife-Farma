package richlife.user.RichLife_User.utils.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import richlife.user.RichLife_User.utils.EnumPhone;

public class EnumPhoneValidator implements ConstraintValidator<EnumPhone, String> {
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null) {
            return false;
        }
        return phone.matches("^0\\d{9,10}$");
    }
}