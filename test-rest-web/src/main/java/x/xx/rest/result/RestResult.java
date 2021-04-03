package x.xx.rest.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * controller层返回model
 * @param <T>
 */
@Data
@AllArgsConstructor
public class RestResult<T> {
    private int code;
    private String msg;
    private T data;
}