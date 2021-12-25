package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.FileNameDto;

public interface BusinessTripServiceInt {

    BusinessTripDto edit(FileNameDto nameDto);

    void delete(Long id);

}
