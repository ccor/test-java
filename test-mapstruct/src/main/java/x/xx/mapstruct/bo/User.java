package x.xx.mapstruct.bo;

import lombok.Data;

import java.util.List;

/**
 * @author ccor2005@gmail.com
 * @date 2021/11/20 11:08
 */
@Data
public class User {
    private long id;
    private String name;
    private List<Address> addresses;
}
