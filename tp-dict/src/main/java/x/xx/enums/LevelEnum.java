package x.xx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
@DictLabel("level")
public enum LevelEnum implements ICodeName {

    HIGH("high", "高"),
    MIDDLE("middle", "中"),
    LOW("low", "低");

    private String code;
    private String name;
}
