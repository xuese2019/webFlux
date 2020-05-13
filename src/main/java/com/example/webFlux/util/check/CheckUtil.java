package com.example.webFlux.util.check;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * 校验工具类
 */
public class CheckUtil {

    public static String check(Object object) {
        Set<ConstraintViolation<Object>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if (validate != null && !validate.isEmpty()) {
            return validate.iterator().next().getMessage();
        }
        return null;
    }
}
