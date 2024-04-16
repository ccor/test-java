/*
*
* User.java
* Copyright(C) 2017-2020 fendo公司
* @date 2020-08-24
*/
package x.xx.domain.mysql;

import java.util.Date;
import lombok.Data;

/**
* Created by Mybatis Generator 2020/08/24
*/
@Data
public class User {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;
}