package richlife.user.RichLife_User.utils.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import richlife.user.RichLife_User.utils.EnumName;

import java.text.Normalizer;

public class EnumNameValidator implements ConstraintValidator<EnumName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            return true;
        }
        name = name.trim();
        if (name.isEmpty()) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if ((!Character.isLetter(c)) && (c != ' ') && (!isVietnameseCharacter(c))) {
                return false;
            }
        }
        return true;
    }

    private boolean isVietnameseCharacter(char c) {

        String normalizedChar = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD);
        return normalizedChar.matches("\\p{InCombiningDiacriticalMarks}+");
    }
}
