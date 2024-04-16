package x.xx.common.model;


import lombok.Getter;
import lombok.Setter;

/**
 * @date 2024/4/16 19:54
 */
@Setter
@Getter
public class R<T> {
    private int code;
    private String msg;
    private T data;
}
