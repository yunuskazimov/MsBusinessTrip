package az.xazar.msbusinesstrip.service;

import az.xazar.msbusinesstrip.entity.BTEntity;
import az.xazar.msbusinesstrip.mapper.BTMapper;
import az.xazar.msbusinesstrip.model.BTDto;
import az.xazar.msbusinesstrip.repository.BTRepo;
import az.xazar.msbusinesstrip.util.BTUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BTServiceImpl implements BTService {
    private final BTRepo repo;
    private final BTUtil util;
    private final BTMapper mapper;

    public BTServiceImpl(BTRepo repo,
                         BTUtil util,
                         BTMapper mapper) {
        this.repo = repo;
        this.util = util;
        this.mapper = mapper;
    }

    @Override
    public BTDto create(BTDto btDto) {
        BTEntity entity = repo.save(
                mapper.toEntity(btDto));
        btDto.setId(entity.getId());
        return btDto;
    }

    @Override
    public BTDto edit(BTDto btDto) {
        util.findBT(btDto.getId());
        repo.save(mapper.toEntity(btDto));
        return btDto;
    }

    @Override
    public BTDto getById(Long id) {
        return mapper.toDto(
                util.findBT(id));
    }

    @Override
    public List<BTDto> getList() {
        return mapper.toDtos(
                repo.findAll());
    }

    @Override
    public List<BTDto> getByUserId(Long userid) {
        return mapper.toDtos(util.findBTByUserId(userid));
    }

    @Override
    public void delete(Long id) {
        BTEntity entity=util.findBT(id);
        entity.setDeleted(true);
        repo.save(entity);
    }
}
