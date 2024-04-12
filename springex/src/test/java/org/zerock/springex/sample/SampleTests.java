package org.zerock.springex.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/* ServletContext : tomcat의 전역영역, tomcat의 Container가 관리하는 영역
*  App
* */
@Log4j2
@ExtendWith(SpringExtension.class)
// root-context.xml을 읽어들여서 A
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/root-context.xml")
public class SampleTests {
    
    // 자동 주입
    // 필드 주입 방식
    // spring이 제공하는 어노테이션
    @Autowired
    private SampleService sampleService;

    @Test
    public void testService1(){
        log.info(sampleService);
        Assertions.assertNotNull(sampleService); // 값이 유효한지를 판별
    }
}
