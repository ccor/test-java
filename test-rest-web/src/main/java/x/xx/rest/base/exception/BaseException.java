package x.xx.rest.base.exception;

import lombok.Getter;
import x.xx.rest.base.CodeMsg;


/**
 * 基本异常
 */
public class BaseException extends RuntimeException {

    @Getter
    private final int code;

    @Getter
    private final String msg;

    public BaseException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(String message) {
        super(message);
        this.code = 500;
        this.msg = message;
    }

    public BaseException(String message, Throwable e) {
        super(message, e);
        this.code = 500;
        this.msg = message;
    }
}
