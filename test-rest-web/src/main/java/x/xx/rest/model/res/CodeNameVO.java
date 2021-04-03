package x.xx.rest.model.res;

import lombok.Data;
import x.xx.rest.base.CodeName;

/**
 * @author ccor
 * @date 2021/4/3 12:18
 */
@Data
public class CodeNameVO implements CodeName {

    private String code;
    private String name;

}
