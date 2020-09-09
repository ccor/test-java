package x.xx.zk.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import x.xx.zk.curator.ZkNodeChange;

@Service
@Log4j2
public class TestZk {

    @ZkNodeChange(path="/xx")
    public void test(String value){
        log.info("--test:{}", value);
    }

}
