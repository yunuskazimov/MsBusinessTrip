package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.client.MinioClient;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.FileDto;
import az.xazar.msbusinesstrip.service.BusinessTripServiceInt;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/int/businesstrip")
public class BTControllerInt {
    private final BusinessTripServiceInt service;
    private final MinioClient minioClient;

    public BTControllerInt(BusinessTripServiceInt service, MinioClient minioClient) {
        this.service = service;
        this.minioClient = minioClient;
    }


    @PostMapping()
    public ResponseEntity<BusinessTripDto> createFileName(@RequestBody FileDto nameDto) {
        return new ResponseEntity<>(
                service.edit(nameDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteFileName(@PathVariable Long id) {
        service.delete(id);
    }


    @PostMapping("/file/{id}")
    @ApiOperation(value = "Add User File to MsMinio")
    public ResponseEntity<String> createFileToMsMinio(@PathVariable("id") Long userId,
                                                       @Valid @RequestParam MultipartFile file,
                                                       @RequestParam String type) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                .body(minioClient.postToMinio(userId, file,type).getBody());
    }
}
