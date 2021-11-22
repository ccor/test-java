package x.xx.mapstruct.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ccor2005@gmail.com
 * @date 2021/11/20 11:17
 */
@Data
public class UserDto {
    private long id;
    private String name;
    private List<AddressDto> addresses;
}
