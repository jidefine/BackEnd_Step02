package org.zerock.springex.sample;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ToString
@RequiredArgsConstructor
public class SampleService {

    @Autowired
    private final SampleDAO sampleDAO;

    /*필드 주입 방식
    @Autowired
    private SampleDAO sampleDAO;*/
}
