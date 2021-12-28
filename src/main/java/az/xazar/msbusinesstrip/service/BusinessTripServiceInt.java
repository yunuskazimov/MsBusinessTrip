package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.FileDto;

public interface BusinessTripServiceInt {

    BusinessTripDto edit(FileDto nameDto);

    void delete(Long id);

}
