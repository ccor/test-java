package org.example.mpp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.annotation.*;

/**
 * @date 2022/2/22 22:01
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface TableMapping {
    TableName name();
    TableId[] pks() default {};
    TableFieldMapping[] fields() default {};
}
