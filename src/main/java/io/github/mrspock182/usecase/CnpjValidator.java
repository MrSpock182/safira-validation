package io.github.mrspock182.usecase;

import io.github.mrspock182.annotation.CnpjValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;

public class CnpjValidator implements ConstraintValidator<CnpjValidation, String> {
    @Override
    public void initialize(CnpjValidation constraintAnnotation) {
        // TODO document why this method is empty
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        if (value.equals("00000000000000") || value.equals("11111111111111") ||
                value.equals("22222222222222") || value.equals("33333333333333") ||
                value.equals("44444444444444") || value.equals("55555555555555") ||
                value.equals("66666666666666") || value.equals("77777777777777") ||
                value.equals("88888888888888") || value.equals("99999999999999") ||
                (value.length() != 14)) {
            return false;
        }

        char dig13;
        char dig14;
        int sm;
        int i;
        int r;
        int num;
        int peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (value.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (value.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            return (dig13 == value.charAt(12)) && (dig14 == value.charAt(13));
        } catch (InputMismatchException error) {
            return false;
        }
    }
}
