package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.model.BTDto;

import java.util.List;

public interface BTService {
    BTDto create(BTDto btDto);

    BTDto edit(BTDto btDto);

    BTDto getById(Long id);

    List<BTDto> getList();

    List<BTDto> getByUserId(Long userid);

    void delete(Long id);
}
