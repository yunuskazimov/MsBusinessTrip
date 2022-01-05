package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BusinessTripDto;

import java.util.List;

public interface BusinessTripService {

    BusinessTripDto create(BusinessTripDto businessTripDto);

    BusinessTripDto edit(Long id, BusinessTripDto businessTripDto);

    String delete(Long id);

    BusinessTripDto getById(Long id);

    String getFileUrlById(Long id);

    List<BusinessTripDto> getList();

    List<BusinessTripDto> getByUserId(Long userid);

}
