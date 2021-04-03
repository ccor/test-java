package x.xx.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import x.xx.rest.annotation.ResWrap;
import x.xx.rest.base.CodeMsg;
import x.xx.rest.base.exception.BaseException;
import x.xx.rest.enums.ResultCodeEnum;
import x.xx.rest.model.res.CodeNameVO;
import x.xx.rest.result.PageList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
public class IndexController {

    @GetMapping("/ping")
    @ResWrap
    public String ping() {
        return "pong";
    }


    @GetMapping("/ret-codes")
    @ResWrap
    public PageList<CodeNameVO> retCodes() {
        PageList<CodeNameVO> page = new PageList<>();
        page.setPageIndex(1);
        page.setPageSize(20);
        List<CodeNameVO> list = Arrays.stream(ResultCodeEnum.values()).map(rc -> {
            CodeNameVO codeNameVO = new CodeNameVO();
            codeNameVO.setCode(String.valueOf(rc.getCode()));
            codeNameVO.setName(rc.getMsg());
            return codeNameVO;
        }).collect(Collectors.toList());
        page.setList(list);
        page.setTotal(list.size());
        return page;
    }

}
