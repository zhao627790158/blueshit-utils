package cn.zh.blueshit.common;

import cn.zh.blueshit.exception.OperateException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

/**
 * Created by zhaoheng on 2016/6/22.
 */
public class ParamValidator {

    public static final Logger logger = LoggerFactory.getLogger(ParamValidator.class);

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> void validate(T obj, Class<?> groupClass) throws OperateException {
        Validator validator = validatorFactory.getValidator();
        StringBuilder msgBuilder = new StringBuilder();
        for (ConstraintViolation<T> constraintViolation : validator.validate(obj, groupClass == null ? Default.class : groupClass)) {
            msgBuilder.append(constraintViolation.getMessage());
            msgBuilder.append(";");
        }
        if (StringUtils.isNotBlank(msgBuilder)) {
            logger.error("Pojo = {}, validate error! msg = {}", obj, msgBuilder.toString());
            throw new OperateException(msgBuilder.toString());
        }
    }


}
