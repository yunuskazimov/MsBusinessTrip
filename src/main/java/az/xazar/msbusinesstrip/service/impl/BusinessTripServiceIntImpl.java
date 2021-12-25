package az.xazar.msbusinesstrip.service.impl;

import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.mapper.BusinessTripMapper;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.FileNameDto;
import az.xazar.msbusinesstrip.repository.BusinessTripRepo;
import az.xazar.msbusinesstrip.service.BusinessTripServiceInt;
import az.xazar.msbusinesstrip.util.BTUtil;
import org.springframework.stereotype.Service;

@Service
public class BusinessTripServiceIntImpl implements BusinessTripServiceInt {

    private final BTUtil util;
    private final BusinessTripRepo repo;
    private final BusinessTripMapper mapper;

    public BusinessTripServiceIntImpl(BTUtil util,
                                      BusinessTripRepo repo,
                                      BusinessTripMapper mapper) {
        this.util = util;
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public BusinessTripDto edit(FileNameDto nameDto) {

        BusinessTripEntity entity = util.findBT(nameDto.getId());
        entity.setScannedFile(nameDto.getScannedFile());
        repo.save(entity);

        return mapper.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        BusinessTripEntity entity = util.findBT(id);
        entity.setScannedFile(null);
        repo.save(entity);
    }

}