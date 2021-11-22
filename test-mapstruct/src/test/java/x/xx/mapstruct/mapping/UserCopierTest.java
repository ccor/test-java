package x.xx.mapstruct.mapping;

import org.junit.jupiter.api.Assertions;
import x.xx.mapstruct.bo.Address;
import x.xx.mapstruct.bo.User;
import x.xx.mapstruct.dto.UserDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ccor2005@gmail.com
 * @date 2021/11/20 11:23
 */
class UserCopierTest {

    @org.junit.jupiter.api.Test
    void userToDtoUser() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        address.setCityCode("10");
        address.setCityName("beijing");
        user.setAddresses(Arrays.asList(address));
        UserDto userDto = UserCopier.INSTANCE.userToDtoUser(user);
        Assertions.assertEquals(user.getId(), userDto.getId());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getAddresses().get(0).getCityCode(), userDto.getAddresses().get(0).getCityCode());

    }

    @org.junit.jupiter.api.Test
    void testUserToDtoUser() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        address.setCityCode("10");
        address.setCityName("beijing");
        user.setAddresses(Arrays.asList(address));
        List<User> users = new ArrayList<>();
        users.add(user);
        user = new User();
        user.setId(2);
        user.setName("xxx");
        users.add(user);
        List<UserDto> userDtoList = UserCopier.INSTANCE.userToDtoUser(users);
        Assertions.assertEquals(users.size(), userDtoList.size());
        Assertions.assertEquals(users.get(0).getId(), userDtoList.get(0).getId());
    }
}