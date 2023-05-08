/**
 * Copyright (c) 2018 CoolChange All rights reserved.
 *
 * https://www.coolchange.tech
 *
 * 版权所有，侵权必究！
 */

package com.dyys.hr.utils;

import com.dyys.hr.exception.RenException;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * 参考文档：http://docs.jboss.org/hibernate/validator/6.0/reference/en-US/html_single/
 *
 * @author
 * @since 1.0.0
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.byDefaultProvider().configure().messageInterpolator(
            new ResourceBundleMessageInterpolator(new MessageSourceResourceBundleLocator(getMessageSource())))
            .buildValidatorFactory().getValidator();
    }

    private static ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding("UTF-8");
        bundleMessageSource.setBasenames("i18n/validation");
        return bundleMessageSource;
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RenException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
        	ConstraintViolation<Object> constraint = constraintViolations.iterator().next();
            throw new RenException(constraint.getMessage());
        }
    }
}