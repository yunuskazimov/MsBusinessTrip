package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.BusinessTripGetDto;
import az.xazar.msbusinesstrip.model.PageDto;
import org.springframework.data.domain.Page;

public interface BusinessTripService {

    BusinessTripDto create(BusinessTripDto businessTripDto);

    BusinessTripDto edit(Long id, BusinessTripDto businessTripDto);

    BusinessTripDto editResult(Long id, String result);

    String delete(Long id);

    BusinessTripDto getById(Long id);

    String getFileUrlById(Long id);

    Page<BusinessTripGetDto> getList(PageDto page);

    Page<BusinessTripGetDto> getByUserId(Long userid, PageDto page);

}
