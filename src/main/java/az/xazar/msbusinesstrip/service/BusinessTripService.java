package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BusinessTripDto;

import java.util.List;

public interface BusinessTripService {

    BusinessTripDto create(BusinessTripDto businessTripDto);

    BusinessTripDto edit(BusinessTripDto businessTripDto);

    BusinessTripDto getById(Long id);

    List<BusinessTripDto> getList();

    List<BusinessTripDto> getByUserId(Long userid);

    void delete(Long id);

}
