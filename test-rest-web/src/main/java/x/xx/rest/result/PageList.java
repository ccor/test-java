package x.xx.rest.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private long total;
    private int pageIndex;
    private int pageSize;
    private List<T> list;
}