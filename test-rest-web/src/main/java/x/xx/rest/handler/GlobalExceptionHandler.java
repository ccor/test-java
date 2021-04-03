package x.xx.rest.handler;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import x.xx.rest.base.exception.BaseException;
import x.xx.rest.result.RestResult;

import javax.validation.ConstraintViolationException;
import java.util.Comparator;
import java.util.Optional;

import static x.xx.rest.enums.ResultCodeEnum.*;
import static x.xx.rest.result.RestResultWrapper.fail;

/**
 * 全局异常处理
 */
@SuppressWarnings("rawtypes")
@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * 处理请求参数格式错误
     * <p>
     * using @Valid without @RequestBody on request parameter,
     * the validation throws a BindException instead of a MethodArgumentNotValidException.
     * 参考：<a href="https://jira.spring.io/browse/SPR-10157">https://jira.spring.io/browse/SPR-10157</a>
     * </p>
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public RestResult handleBindException(BindException e) {
        return fail(INVALID_PARAM.getCode(), getFirstFieldErrorMsg(e.getBindingResult()));
    }

    /**
     * 处理请求参数格式错误
     * <p>@RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常</p>
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return fail(INVALID_PARAM.getCode(), getFirstFieldErrorMsg(e.getBindingResult()));
    }

    /**
     * 处理请求参数格式错误
     * <p>@RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException</p>
     *
     * @param e
     * @return
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public RestResult handleConstraintViolationException(ConstraintViolationException e) {
        return fail(INVALID_PARAM.getCode(), e.getConstraintViolations().toString());
    }

    /**
     * 处理请求方法不支持
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return fail(REQ_METHOD_NOT_SUPPORTED);
    }

    /**
     * 处理请求体解析失败
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return fail(INVALID_REQ_BODY);
    }

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public RestResult handleBizException(BaseException e) {
        log.warn("code:" + e.getCode(), e);
        int code = e.getCode();
        String msg = e.getMessage();
        return fail(code, msg);
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public RestResult exceptionHandler(Exception e) {
        log.warn("unknown error:", e);
        return fail(SERVICE_ERROR.getCode(), e.getMessage());
    }


    private String getFirstFieldErrorMsg(BindingResult bindingResult) {
        return Optional.of(bindingResult.getFieldErrors())
                .flatMap(fieldErrors -> fieldErrors.stream().min(Comparator.comparing(FieldError::getField)))
                .map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
                .orElse(null);
    }

//    NoHandlerFoundException
//    MissingServletRequestParameterException
//    MethodArgumentTypeMismatchException


}
