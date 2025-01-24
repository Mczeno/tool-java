package io.github.mczeno.tool;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * ValidationUtil
 *
 * @author Chongming Zhou
 */
public class ValidationUtil {

    private static final Validator validator;

    static {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
    }

    private ValidationUtil() {
        // 私有构造函数防止实例化
    }

    /**
     * 验证对象并返回所有约束违反
     *
     * @param object 要验证的对象
     *
     * @return 包含所有约束违反的Set
     */
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }

    /**
     * 验证对象并返回第一个约束违反（如果存在）
     *
     * @param object 要验证的对象
     *
     * @return 第一个约束违反，如果没有则返回null
     */
    public static <T> ConstraintViolation<T> validateFirst(T object) {
        Set<ConstraintViolation<T>> violations = validate(object);
        return violations.isEmpty() ? null : violations.iterator().next();
    }

    /**
     * 验证对象并返回所有约束违反的消息
     *
     * @param object 要验证的对象
     *
     * @return 包含所有约束违反消息的Set
     */
    public static <T> Set<String> validateAndGetMessages(T object) {
        return validate(object).parallelStream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.toSet());
    }

    /**
     * 验证对象并抛出异常（如果存在任何约束违反）
     *
     * @param object 要验证的对象
     *
     * @throws ValidationException 验证异常
     */
    public static <T> void validateAndThrow(T object) {
        Set<String> messages = validateAndGetMessages(object);
        if (!messages.isEmpty()) {
            throw new ValidationException(String.join("; ", messages));
        }
    }

    /**
     * 验证对象并返回是否有效
     *
     * @param object 要验证的对象
     *
     * @return 如果对象有效则为true，否则为false
     */
    public static <T> boolean isValid(T object) {
        return validate(object).isEmpty();
    }

}
