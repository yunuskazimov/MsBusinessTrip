package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BTDto;
import az.xazar.msbusinesstrip.model.FileNameDto;

public interface BTServiceInt {
     BTDto edit(FileNameDto nameDto);
     void delete(Long id);
}
