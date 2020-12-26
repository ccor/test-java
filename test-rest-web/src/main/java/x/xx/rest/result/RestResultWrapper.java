package x.xx.rest.result;


import x.xx.rest.base.CodeMsg;

import static x.xx.rest.enums.ResultCodeEnum.RC_SUCCESS;

/**
 * 返回对象工具类
 */
public class RestResultWrapper {
    public static <T> RestResult<T> fail(CodeMsg codeMsg) {
        return new RestResult<>(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    public static <T> RestResult<T> fail(int code, String msg) {
        return new RestResult<>(code, msg, null);
    }

    public static <T> RestResult<T> success() {
        return new RestResult<>(RC_SUCCESS.getCode(), RC_SUCCESS.getMsg(), null);
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(RC_SUCCESS.getCode(), RC_SUCCESS.getMsg(), data);
    }

}
