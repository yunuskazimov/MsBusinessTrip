package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.client.MinioClient;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.service.BusinessTripService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/businesstrip")
public class BusinessTripController {
    private final BusinessTripService service;
    private final MinioClient minioClient;

    public BusinessTripController(BusinessTripService service, MinioClient minioClient) {
        this.service = service;
        this.minioClient = minioClient;
    }

    @PostMapping(value = "/file", consumes = {"multipart/form-data"})
    @ApiOperation(value = "Add User File to MsMinio")
    public BusinessTripDto createWithFile(
            @ModelAttribute BusinessTripDto businessTripDto) {
        log.info("create With File started controller with {}",
                kv("fileDto", businessTripDto));
        return service.create(businessTripDto);
    }

    @PutMapping(value = "/file/{id}", consumes = {"multipart/form-data"})
    @ApiOperation(value = "Edit User File to MsMinio")
    public BusinessTripDto editWithFile(@PathVariable Long id,
                                        @ModelAttribute BusinessTripDto businessTripDto) {
        log.info("edit With File started controller with {}",
                kv("businessTrip", businessTripDto));
        return service.edit(id, businessTripDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.delete(id));
    }

    @GetMapping()
    public List<BusinessTripDto> getBusinessTripList() {
        return service.getList();
    }

    @GetMapping("id/{id}")
    public BusinessTripDto getBusinessTripById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("file/{id}")
    public String getFileUrlById(@PathVariable Long id) {
        return service.getFileUrlById(id);
    }

    @GetMapping("/uid/{uId}")
    public List<BusinessTripDto> getByUserId(@PathVariable Long uId) {
        return service.getByUserId(uId);
    }


    //    @PostMapping()
//    public ResponseEntity<BusinessTripDto> create(@RequestBody BusinessTripDto dto) {
//        return new ResponseEntity<>(
//                service.create(dto), HttpStatus.CREATED);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BusinessTripDto> edit(@RequestBody BusinessTripDto dto,
//                                                @PathVariable Long id) {
//        dto.setId(id);
//        return new ResponseEntity<>(
//                service.edit(dto), HttpStatus.OK);
//    }

//    @PostMapping(value = "/file",consumes = {"multipart/form-data"})
//    @ApiOperation(value = "Add User File to MsMinio")
//    public ResponseEntity<FileDto> createFile(@ModelAttribute FileDto fileDto) {
//        log.info("create File started controller with {}", kv("fileDto",fileDto));
//        return ResponseEntity.status(HttpStatus.MULTI_STATUS)
//                .body(service.createFile(fileDto).getBody());
//    }

//    @DeleteMapping("/file/{id}")
//    public void deleteById(@PathVariable Long id) {
//        service.delete(id);
//    }


}
