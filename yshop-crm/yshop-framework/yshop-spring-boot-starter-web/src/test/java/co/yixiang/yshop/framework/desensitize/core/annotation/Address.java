package co.yixiang.yshop.framework.desensitize.core.annotation;

import co.yixiang.yshop.framework.desensitize.core.DesensitizeTest;
import co.yixiang.yshop.framework.desensitize.core.base.annotation.DesensitizeBy;
import co.yixiang.yshop.framework.desensitize.core.handler.AddressHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 地址
 *
 * 用于 {@link DesensitizeTest} 测试使用
 *
 * @author gaibu
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = AddressHandler.class)
public @interface Address {

    String replacer() default "*";

}
