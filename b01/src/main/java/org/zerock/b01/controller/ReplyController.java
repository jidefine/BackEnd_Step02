package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.ReplyDTO;

import java.util.Map;

@RestController // REST방식 : json 방식으로
@RequestMapping("/replies")
@Log4j2
public class ReplyController {
    @ApiOperation(value = "Relies POST", notes  = "POST 방식으로 댓글 등록") //swagger-ui에 보여짐
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)

    // json 문자열으로 들어오는
    public ResponseEntity<Map<String,Long>> register(@RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Map<String, Long> resultMap = Map.of("rno",111L);

        return ResponseEntity.ok(resultMap);
    }
}
