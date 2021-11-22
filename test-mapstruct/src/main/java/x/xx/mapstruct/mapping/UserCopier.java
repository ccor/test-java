package x.xx.mapstruct.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import x.xx.mapstruct.bo.User;
import x.xx.mapstruct.dto.UserDto;

import java.util.List;

/**
 * @author chenrong30@gome.com.cn
 * @date 2021/11/20 11:11
 */
@Mapper
public interface UserCopier {
    UserCopier INSTANCE = Mappers.getMapper(UserCopier.class);

    @Mappings({})
    UserDto userToDtoUser(User user);

    List<UserDto> userToDtoUser(List<User> list);

}
