package x.xx.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import x.xx.dto.CodeNameDTO;
import x.xx.utils.DictUtils;

import java.util.List;
import java.util.Map;

@RestController
public class DictController {

    @GetMapping("/getAllDict")
    public Map<String, List<CodeNameDTO>> getAllDict() {
        return DictUtils.getAllDict();
    }

    @GetMapping("/getDict")
    public List<CodeNameDTO> getAllDict(@RequestParam String type) {
        return DictUtils.getAllDict().get(type);
    }
}
