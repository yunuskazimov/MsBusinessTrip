package az.xazar.msbusinesstrip.service.impl;

import az.xazar.msbusinesstrip.client.MinioClient;
import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.exception.BusinessTripNotFoundException;
import az.xazar.msbusinesstrip.exception.FileNotFoundException;
import az.xazar.msbusinesstrip.mapper.BusinessTripMapper;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.FileDto;
import az.xazar.msbusinesstrip.repository.BusinessTripRepo;
import az.xazar.msbusinesstrip.service.BusinessTripService;
import az.xazar.msbusinesstrip.util.BusinessTripUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Service
public class BusinessTripServiceImpl implements BusinessTripService {

    private final BusinessTripRepo businessTripRepo;
    private final BusinessTripUtil util;
    private final BusinessTripMapper businessTripMapper;
    private final MinioClient minioClient;

    public BusinessTripServiceImpl(BusinessTripRepo businessTripRepo,
                                   BusinessTripUtil util,
                                   BusinessTripMapper businessTripMapper,
                                   MinioClient minioClient) {
        this.businessTripRepo = businessTripRepo;
        this.util = util;
        this.businessTripMapper = businessTripMapper;
        this.minioClient = minioClient;
    }

    @Override
    public BusinessTripDto create(BusinessTripDto businessTripDto) {

        uploadFile(businessTripDto);

        BusinessTripEntity entity =
                businessTripRepo.save(businessTripMapper.toBusinessTripEntity(businessTripDto));
        businessTripDto.setId(entity.getId());

        return businessTripDto;
    }

    private void uploadFile(BusinessTripDto businessTripDto) {
        if (businessTripDto.getFile() != null) {
            FileDto fileDto = minioClient.postToMinio(FileDto.builder()
                    .file(businessTripDto.getFile())
                    .userId(businessTripDto.getUserId())
                    .type("Business Trip")
                    .build()).getBody();
            businessTripDto.setFileId(fileDto.getFileId());
            businessTripDto.setFileName(fileDto.getFileName());
        }
    }

    @Override
    public BusinessTripDto edit(Long id, BusinessTripDto businessTripDto) {

        log.info("edit With File started Service with {}", kv("businessTrip", businessTripDto));

        BusinessTripEntity entity = util.findBusinessTrip(id);
        checkIsDeleted(entity);

        updateFile(businessTripDto, entity);

        businessTripDto.setUserId(entity.getUserId());
        log.info("edit With File completed Service with {}", kv("businessTrip", businessTripDto));

        return businessTripMapper.toBusinessTripDto(
                businessTripRepo.save(businessTripMapper.toBusinessTripEntity(businessTripDto)));
    }

    private void updateFile(BusinessTripDto businessTripDto, BusinessTripEntity entity) {
        if (!businessTripDto.getFile().isEmpty()) {
            FileDto fileDto = minioClient.putToMinio(FileDto.builder()
                    .fileId(entity.getFileId())
                    .file(businessTripDto.getFile())
                    .userId(entity.getUserId())
                    .type("Business Trip")
                    .build()).getBody();

            businessTripDto.setFileId(fileDto != null ? fileDto.getFileId() : entity.getFileId());
            businessTripDto.setFileName(fileDto != null ? fileDto.getFileName() : entity.getFileName());
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

    @Override
    public String delete(Long id) {
        BusinessTripEntity entity = util.findBusinessTrip(id);
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

    private void deleteFileWithCheck(BusinessTripEntity entity) {
        if (entity.getFileId() != null) {
            minioClient.deleteToMinio(entity.getFileId());
        }
    }

    @Override
    public BusinessTripDto getById(Long id) {

        BusinessTripEntity tripEntity = util.findBusinessTrip(id);

        return businessTripMapper.toBusinessTripDto(tripEntity);

    }

    @Override
    public String getFileUrlById(Long id) {
        BusinessTripEntity tripEntity = util.findBusinessTrip(id);
        if (tripEntity.getFileName() != null) {
            return minioClient.getFromMinio(tripEntity.getFileName());
        }
        return null;
    }


    @Override
    public List<BusinessTripDto> getList() {
        return businessTripMapper.toBusinessTripDtoList(businessTripRepo.findAll());
    }

    @Override
    public List<BusinessTripDto> getByUserId(Long userid) {
        util.findBusinessTripByUserId(userid);
        return businessTripMapper
                .toBusinessTripDtoList(util.findBusinessTripListByUserId(userid));
    }


}
