package x.xx.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import x.xx.rest.annotation.ResWrap;
import x.xx.rest.base.CodeMsg;
import x.xx.rest.base.exception.BaseException;
import x.xx.rest.enums.ResultCodeEnum;
import x.xx.rest.model.res.CodeNameVO;
import x.xx.rest.result.PageList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    @GetMapping("/x")
    public void x(HttpServletResponse res) throws IOException {
        StringBuilder sb = new StringBuilder(10000);
        for(int i = 0; i < 9000; i ++) {
            sb.append("0");
        }
        res.getOutputStream().write(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

}
