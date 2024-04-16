package org.example.mpp;

import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.annotation.*;

/**
 * @date 2022/2/23 10:51
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface TableFieldMapping {
    String value();
    TableField field();
    // todo 这个怎么设置？ @TableLogic
}
