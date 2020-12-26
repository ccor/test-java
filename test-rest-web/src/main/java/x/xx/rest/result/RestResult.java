package x.xx.rest.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestResult<T> {
    private int code;
    private String msg;
    private T data;
}