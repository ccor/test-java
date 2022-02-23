package x.xx.utils;


import x.xx.dto.CodeNameDTO;
import x.xx.enums.ICodeName;
import x.xx.enums.LevelEnum;

import java.util.*;
import java.util.stream.Collectors;

public class DictUtils2 {
    private static final Map<String, List<CodeNameDTO>> dictCacheMap;

    static {
        Map<String, List<CodeNameDTO>> map = new HashMap<>();
        map.put("level", dict(LevelEnum.values()));
        //map.put("sex", dict(SexEnum.values()));
        dictCacheMap = Collections.unmodifiableMap(map);

    }


    public static Map<String, List<CodeNameDTO>> getAllDict() {
        return dictCacheMap;
    }

    /**
     * 字典类枚举接口转化为实体对象
     */
    private static List<CodeNameDTO> dict(ICodeName[] codeNames) {
        List<CodeNameDTO> list = Arrays.stream(codeNames).map(i -> {
            CodeNameDTO codeNameDTO = new CodeNameDTO();
            codeNameDTO.setCode(i.getCode());
            codeNameDTO.setName(i.getName());
            return codeNameDTO;
        }).collect(Collectors.toList());
        return Collections.unmodifiableList(list);
    }
}
