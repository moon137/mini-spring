package com.jing.framework.web.mvc;

import java.lang.annotation.*;

/**
 * @author GUO
 * @date 2019/9/24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParam {
    String value() ;
}
