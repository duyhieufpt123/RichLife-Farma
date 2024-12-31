package richlife.user.RichLife_User.utils.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import richlife.user.RichLife_User.utils.EnumWhiteSpace;


public class EnumWhiteSpaceValidator implements ConstraintValidator<EnumWhiteSpace, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
}