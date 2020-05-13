package com.example.webFlux.util.check;

import com.example.webFlux.exception.MyException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * 校验工具类
 */
public class CheckUtil {

    public static void check(Object object) {
        Set<ConstraintViolation<Object>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(object);
        if (validate != null && !validate.isEmpty()) {
            throw new MyException(validate.iterator().next().getMessage());
//            return ServerResponse
//                    .status(HttpStatus.BAD_REQUEST)
//                    .bodyValue(validate.iterator().next().getMessage());
        }
    }
}
