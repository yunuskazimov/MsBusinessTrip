package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.entity.BTEntity;
import az.xazar.msbusinesstrip.mapper.BTMapper;
import az.xazar.msbusinesstrip.model.BTDto;
import az.xazar.msbusinesstrip.model.FileNameDto;
import az.xazar.msbusinesstrip.repository.BTRepo;
import az.xazar.msbusinesstrip.util.BTUtil;
import org.springframework.stereotype.Service;

@Service
public class BTServiceIntImpl implements BTServiceInt {
    private final BTUtil util;
    private final BTRepo repo;
    private final BTMapper mapper;

    public BTServiceIntImpl(BTUtil util,
                            BTRepo repo,
                            BTMapper mapper) {
        this.util = util;
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public BTDto edit(FileNameDto nameDto) {

        BTEntity entity = util.findBT(nameDto.getId());
        entity.setScannedFile(nameDto.getScannedFile());
        repo.save(entity);

        return mapper.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        BTEntity entity = util.findBT(id);
        entity.setScannedFile(null);
        repo.save(entity);
    }

}
