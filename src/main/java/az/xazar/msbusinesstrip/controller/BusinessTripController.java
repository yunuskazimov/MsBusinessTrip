package az.xazar.msbusinesstrip.controller;

import az.xazar.msbusinesstrip.client.MinioClient;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.BusinessTripGetDto;
import az.xazar.msbusinesstrip.model.PageDto;
import az.xazar.msbusinesstrip.service.BusinessTripService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@RestController
@RequestMapping("/api/businesstrip")
@CrossOrigin
public class BusinessTripController {
    private final BusinessTripService service;
    private final MinioClient minioClient;

    public BusinessTripController(BusinessTripService service, MinioClient minioClient) {
        this.service = service;
        this.minioClient = minioClient;
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @PostMapping(consumes = {"multipart/form-data"})
    @ApiOperation(value = "Add Business Trip with File")
    public BusinessTripDto createWithFile(
            @RequestHeader(name = "User-Id") Long userId,
            @ModelAttribute BusinessTripDto businessTripDto) {
        log.info("createWithFile started controller with {}",
                kv("fileDto", businessTripDto));
        businessTripDto.setUserId(userId);
        return service.create(businessTripDto);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'ADMIN'})")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @ApiOperation(value = "Edit Business Trip with File")
    public BusinessTripDto editWithFile(@RequestHeader(name = "User-Id") Long userId,
                                        @PathVariable Long id,
                                        @ModelAttribute BusinessTripDto businessTripDto) {
        log.info("editWithFile started controller with {}",
                kv("businessTrip", businessTripDto));
        return service.edit(id, businessTripDto);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'ADMIN'})")
    @PutMapping("/result")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BusinessTripDto editResult(@RequestHeader(name = "User-Id") Long userId,
                                      @RequestBody ResultForum resultForum) {
        return service.editResult(resultForum.getId(), resultForum.getResult());
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@RequestHeader(name = "User-Id") Long userId,
                                             @PathVariable Long id) {
        log.info("deleteById started controller with {}",
                kv("id", id));
        return ResponseEntity.ok().body(service.delete(id));
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @GetMapping()
    public Page<BusinessTripGetDto> getBusinessTripList(@RequestHeader(name = "User-Id") Long userId,
                                                        PageDto page) {
        return service.getList(page);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @GetMapping("/id/{id}")
    public BusinessTripDto getBusinessTripById(@RequestHeader(name = "User-Id") Long userId,
                                               @PathVariable Long id) {
        return service.getById(id);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'USER'})")
    @GetMapping("/uid")
    public Page<BusinessTripGetDto> getByUserId(@RequestHeader(name = "User-Id") Long userId,
                                                PageDto page) {
        return service.getByUserId(userId, page);
    }

    @PreAuthorize(value = "@permissionService.checkRole(#userId, {'ADMIN'})")
    @GetMapping("/file/{id}")
    public String getFileUrlById(@RequestHeader(name = "User-Id") Long userId,
                                 @PathVariable Long id) {
        return service.getFileUrlById(id);
    }

}

@Data
class ResultForum {
    private Long id;
    private String result;
}
