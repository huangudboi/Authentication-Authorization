package com.example.securityapp.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password paramA) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext ctx) {
        if(password == null){
            return false;
        }
//        + Có 6-20 kí tự
//        + Phải chứa ít nhất 1 ký tự số từ 0 – 9
//        + Phải chứa ít nhất 1 ký tự chữ thường
//        + Phải chứa ít nhất 1 ký tự chữ hoa
        Pattern passValid = Pattern.compile("((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
        return passValid.matcher(password).matches();
    }
}
