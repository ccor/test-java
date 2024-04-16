package x.test.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author chenrong30@gome.com.cn
 * @date 2021/6/4 09:15
 */
@Entity
@Table(name = "t_user")
@Data
public class User implements Serializable {

    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

    @Column(name="user_name")
    private String userName;

    @Column
    private String password;
}
