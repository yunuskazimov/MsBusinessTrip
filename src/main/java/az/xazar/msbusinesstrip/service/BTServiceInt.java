package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BTDto;

public interface BTServiceInt {
     BTDto edit(String fileName, Long id);
     void delete(Long id);
}
