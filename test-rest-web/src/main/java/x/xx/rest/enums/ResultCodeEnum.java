package x.xx.rest.enums;

import lombok.Getter;
import x.xx.rest.base.CodeMsg;

/**
 * 返回代码枚举
 */
@Getter
public enum ResultCodeEnum implements CodeMsg {

    RC_SUCCESS(200, "操作成功"),
    RC_INVALID_REQ_BODY(400, "无效请求参数体"),
    RC_REQ_METHOD_NOT_SUPPORTED(405, "请求的METHOD不支持"),
    RC_UNAUTHORIZED(401, "用户获取失败"),
    RC_NO_ERROR(404, "请求地址不存在"),
    RC_SERVICE_ERROR(500, "系统内部错误"),
    RC_INVALID_PARAM(6001000, "参数错误");


    private final int code;
    private final String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
