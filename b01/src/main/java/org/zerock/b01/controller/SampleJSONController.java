package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class SampleJSONController {

    @GetMapping("/helloArr")
    public String[] helloArr(){
        log.info("helloArr.............");
        // @RestController는 "/helloArr" 주소로 접속하면 아래 JSON 데이터를 보내줌
        return new String[]{"AAA", "BBB", "CCC"};
    }
}
