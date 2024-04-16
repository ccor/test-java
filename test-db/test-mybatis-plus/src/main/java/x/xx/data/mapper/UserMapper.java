package x.xx.data.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.example.mpp.TableFieldMapping;
import org.example.mpp.TableMapping;
import x.xx.data.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author mybatis-plus
 * @since 2020-11-02
 */
@TableMapping(
        name = @TableName("t_user"),
        pks = { @TableId(value = "id", type = IdType.AUTO) },
        fields = { @TableFieldMapping(value = "userName", field = @TableField("user_name"))}
)
public interface UserMapper extends BaseMapper<UserEntity> {

}
