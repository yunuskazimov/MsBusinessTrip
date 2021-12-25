package az.xazar.msbusinesstrip.service.impl;

import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.mapper.BusinessTripMapper;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.repository.BusinessTripRepo;
import az.xazar.msbusinesstrip.service.BusinessTripService;
import az.xazar.msbusinesstrip.util.BTUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTripServiceImpl implements BusinessTripService {

    private final BusinessTripRepo repo;
    private final BTUtil util;
    private final BusinessTripMapper mapper;

    public BusinessTripServiceImpl(BusinessTripRepo repo, BTUtil util, BusinessTripMapper mapper) {
        this.repo = repo;
        this.util = util;
        this.mapper = mapper;
    }

    @Override
    public BusinessTripDto create(BusinessTripDto businessTripDto) {
        BusinessTripEntity entity = repo.save(mapper.toEntity(businessTripDto));
        businessTripDto.setId(entity.getId());
        return businessTripDto;
    }

    @Override
    public BusinessTripDto edit(BusinessTripDto businessTripDto) {
        util.findBT(businessTripDto.getId());
        repo.save(mapper.toEntity(businessTripDto));
        return businessTripDto;
    }

    @Override
    public BusinessTripDto getById(Long id) {
        return mapper.toDto(
                util.findBT(id));
    }

    @Override
    public List<BusinessTripDto> getList() {
        return mapper.toDtos(repo.findAll());
    }

    @Override
    public List<BusinessTripDto> getByUserId(Long userid) {
        return mapper.toDtos(util.findBTByUserId(userid));
    }

    @Override
    public void delete(Long id) {
        BusinessTripEntity entity = util.findBT(id);
        entity.setDeleted(true);
        repo.save(entity);
    }

}
