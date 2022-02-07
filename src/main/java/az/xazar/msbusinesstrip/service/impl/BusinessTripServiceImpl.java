package az.xazar.msbusinesstrip.service.impl;

import az.xazar.msbusinesstrip.client.MinioClient;
import az.xazar.msbusinesstrip.client.UserClientRest;
import az.xazar.msbusinesstrip.dao.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.dao.repository.BusinessTripRepo;
import az.xazar.msbusinesstrip.exception.BusinessTripNotFoundException;
import az.xazar.msbusinesstrip.exception.FileNotFoundException;
import az.xazar.msbusinesstrip.mapper.BusinessTripMapper;
import az.xazar.msbusinesstrip.model.*;
import az.xazar.msbusinesstrip.model.client.user.UserDto;
import az.xazar.msbusinesstrip.service.BusinessTripService;
import az.xazar.msbusinesstrip.util.BusinessTripUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BusinessTripServiceImpl implements BusinessTripService {

    private final UserClientRest userClient;
    private final BusinessTripRepo businessTripRepo;
    private final BusinessTripUtil businessTripUtil;
    private final BusinessTripMapper businessTripMapper;
    private final MinioClient minioClient;

    @Override
    public BusinessTripDto create(BusinessTripDto businessTripDto) {
        log.info("create service started Service with {}", kv("businessTrip", businessTripDto));
        userClient.getById(businessTripDto.getUserId());
        uploadFile(businessTripDto);
        businessTripDto.setResult(Result.PENDING);
        businessTripDto.setDeleted(false);
        BusinessTripEntity entity =
                businessTripRepo.save(businessTripMapper.toBusinessTripEntity(businessTripDto));
        businessTripDto.setId(entity.getId());
        return businessTripMapper.toBusinessTripDto(entity);
    }

    @Override
    public BusinessTripDto edit(Long id, BusinessTripDto businessTripDto) {
        log.info("edit service started Service with {}", kv("businessTrip", businessTripDto));
        userClient.getById(businessTripDto.getUserId());
        BusinessTripEntity entity = businessTripUtil.findBusinessTrip(id);
        checkIsDeleted(entity);
        checkResult(businessTripDto, entity);
        updateFile(businessTripDto, entity);
        businessTripDto.setUserId(entity.getUserId());
        log.info("edit With File completed Service with {}", kv("businessTrip", businessTripDto));

        return businessTripMapper.toBusinessTripDto(
                businessTripRepo.save(businessTripMapper.toBusinessTripEntity(businessTripDto)));
    }

    @Override
    public BusinessTripDto editResult(Long id, String result) {
        log.info("editResult service started with id: {}, Result: {}", id, result);
        BusinessTripDto businessTripDto = getById(id);
        userClient.getById(businessTripDto.getUserId());
        checkResult(result, businessTripDto);
        businessTripRepo.save(businessTripMapper.toBusinessTripEntity(businessTripDto));
        log.info("editResult service completed with id: {}, Result: {}", id, result);

        return businessTripDto;
    }

    @Override
    public String delete(Long id) {
        log.info("delete service already started. {}", kv("ID ", id));
        BusinessTripEntity entity = businessTripUtil.findBusinessTrip(id);
        userClient.getById(entity.getUserId());
        if (!entity.isDeleted()) {
            entity.setDeleted(true);
            deleteFileWithCheck(entity);
            businessTripRepo.save(entity);
            log.info("delete completed. {}", kv("Business Trip ID", entity.getId()));
            return "Business Trip Deleted.";
        }
        log.info("delete already completed. {}", kv("Deleted Time", entity.getUpdatedAt()));
        throw new BusinessTripNotFoundException(
                "Business Trip Already Deleted. ID: " + entity.getId()
                        + ", Delete Time: " + entity.getUpdatedAt());
    }

    @Override
    public BusinessTripDto getById(Long id) {
        log.info("getById service already started. {}", kv("ID ", id));
        BusinessTripEntity tripEntity = businessTripUtil.findBusinessTrip(id);
        userClient.getById(tripEntity.getUserId());
        return businessTripMapper.toBusinessTripDto(tripEntity);

    }

    @Override
    public String getFileUrlById(Long id) {
        log.info("getFileUrlById service already started. {}", kv("ID ", id));
        BusinessTripEntity tripEntity = businessTripUtil.findBusinessTrip(id);
        userClient.getById(tripEntity.getUserId());
        if (tripEntity.getFileName() != null) {
            return minioClient.getFromMinio(tripEntity.getFileName());
        }
        return null;
    }

    @Override
    public Page<BusinessTripGetDto> getList(PageDto page) {
        log.info("getList service already started.");
        Page<BusinessTripGetDto> getDto = businessTripRepo.findByDeletedFalse(getPageable(page))
                .map(entity -> {
                    UserDto user = userClient.getById(entity.getUserId());
                    BusinessTripGetDto dto = businessTripMapper.toBusinessTripGetDto(entity);
                    dto.setUserName(user.getName() + " " + user.getSurname());
                    return dto;
                });
        if (!getDto.isEmpty()) {
            log.info("getList service completed ");
            return getDto;
        } else {
            log.info("getList service run exception ");
            throw new BusinessTripNotFoundException("Business Trip Not Found");
        }
    }

    @Override
    public Page<BusinessTripGetDto> getByUserId(Long userid, PageDto page) {
        log.info("getByUserId service already started. {}", kv("User ID ", userid));
        UserDto user = userClient.getById(userid);
        Page<BusinessTripGetDto> getDto = businessTripUtil.findBusinessTripByUserId(userid, getPageable(page))
                .map(entity -> {
                    BusinessTripGetDto dto = businessTripMapper.toBusinessTripGetDto(entity);
                    dto.setUserName(user.getName() + " " + user.getSurname());
                    return dto;
                });
        if (!getDto.isEmpty()) {
            log.info("getByUserId service completed ");
            return getDto;
        } else {
            log.info("getByUserId service run exception ");
            throw new BusinessTripNotFoundException("Business Trip Not Found");
        }
    }

    private void checkResult(BusinessTripDto businessTripDto, BusinessTripEntity entity) {
        if (!entity.getResult().equals(businessTripDto.getResult()) && businessTripDto.getResult() != null) {
            editResult(businessTripDto.getId(), entity.getResult().name());
        } else if (businessTripDto.getResult() == null) {
            businessTripDto.setResult(entity.getResult());
        }
    }

    private Pageable getPageable(PageDto page) {
        Sort sort = Sort.by(page.getSortDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private void uploadFile(BusinessTripDto businessTripDto) {
        log.info("uploadFile started Service with {}", kv("businessTrip", businessTripDto));
        if (businessTripDto.getFile() != null && !businessTripDto.getFile().isEmpty()) {
            FileDto fileDto = minioClient.postToMinio(FileDto.builder()
                    .file(businessTripDto.getFile())
                    .userId(businessTripDto.getUserId())
                    .type("Business Trip")
                    .build()).getBody();
            assert fileDto != null;
            businessTripDto.setFileId(fileDto.getFileId());
            businessTripDto.setFileName(fileDto.getFileName());
        }
    }

    private void checkResult(String result, BusinessTripDto dto) {
        if (result.equalsIgnoreCase(Result.APPROVED.toLower())
                && dto.getResult().equals(Result.PENDING)) {
            dto.setResult(Result.APPROVED);
        } else if (result.equalsIgnoreCase(Result.COMPLETED.toLower())
                && dto.getResult().equals(Result.APPROVED)) {
            dto.setResult(Result.COMPLETED);
        } else if (result.equalsIgnoreCase(Result.DECLINED.toLower())) {
            dto.setResult(Result.DECLINED);
        } else if (dto.getResult().equals(Result.valueOf(result))) {
            log.info("Result is same as DB");
        } else {
            throw new RuntimeException("Wrong Result Name");
        }
    }

    private void updateFile(BusinessTripDto businessTripDto, BusinessTripEntity entity) {
        log.info("updateFile Service started with {}", kv("businessTrip", businessTripDto));
        if (!businessTripDto.getFile().isEmpty()) {
            FileDto fileDto = minioClient.putToMinio(FileDto.builder()
                    .fileId(entity.getFileId())
                    .file(businessTripDto.getFile())
                    .userId(entity.getUserId())
                    .type("Business Trip")
                    .build()).getBody();

            businessTripDto
                    .setFileId(fileDto != null ? fileDto.getFileId() : entity.getFileId());
            businessTripDto
                    .setFileName(fileDto != null ? fileDto.getFileName() : entity.getFileName());
        } else {
            businessTripDto.setFileId(entity.getFileId());
            businessTripDto.setFileName(entity.getFileName());
        }
    }

    private void checkIsDeleted(BusinessTripEntity entity) {
        if (entity.isDeleted()) {
            throw new FileNotFoundException("File Already Deleted. ID:"
                    + entity.getId() + ", Delete Time: " + entity.getUpdatedAt());
        }
    }

    private void deleteFileWithCheck(BusinessTripEntity entity) {
        if (entity.getFileId() != null) {
            minioClient.deleteToMinio(entity.getFileId());
        }
    }

}
