package org.zerock.b01.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.upload.UploadFileDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@Log4j2
public class UpDownController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @ApiOperation(value = "Upload POST", notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public String upload(UploadFileDTO uploadFileDTO){
        log.info(uploadFileDTO);

        // 업로드된 파일들이 존재한다면
        if(uploadFileDTO.getFiles() != null){
            uploadFileDTO.getFiles().forEach(multipartFile -> {

                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);

                //업로드된 파일명을 출력하라.
                log.info(multipartFile.getOriginalFilename());

                // 파일명은 중복가능성이 높기 때문에 중복되지 않는 이름이 필요하다
                // 그래서 중복 확률이 천문학적으로 낮은 UUID를 생성해서 합쳐서 저장할 것이다
                String uuid = UUID.randomUUID().toString();

                // 경로와 중복되지 않는 파일 이름의 경로를 생성
                Path savePath = Paths.get(uploadPath, uuid+"_"+originalName);

                try{
                    // 해당 경로에 파일 저장
                    multipartFile.transferTo(savePath);

                    // 이미지 타입의 파일이라면 썸네일 파일(작은 이미지 파일)을 만든다.
                    // 썸네일 이미지는 기존 이미지 앞에 s_를 붙인 이름이다.
                    if (Files.probeContentType(savePath).startsWith(("image"))) {

                        File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);

                        // 200 x 200 크기의 파일을 동일한 경로에 저장한다.
                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                    }

                } catch (IOException e){
                    e.printStackTrace();
                }
            });
        }

        return null;
    }
}
