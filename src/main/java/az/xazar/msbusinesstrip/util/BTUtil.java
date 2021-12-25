package az.xazar.msbusinesstrip.util;

import az.xazar.msbusinesstrip.entity.BTEntity;
import az.xazar.msbusinesstrip.model.exception.BTNotFoundException;
import az.xazar.msbusinesstrip.model.exception.ErrorCodes;
import az.xazar.msbusinesstrip.repository.BTRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BTUtil {
    private final BTRepo repo;

    public BTUtil(BTRepo repo) {
        this.repo = repo;
    }

    public BTEntity findBT(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new BTNotFoundException(ErrorCodes.NOT_FOUND));
    }

    public List<BTEntity> findBTByUserId(Long userId) {
        return repo.findAllByUserId(userId)
                .orElseThrow(() ->
                        new BTNotFoundException(ErrorCodes.NOT_FOUND));
    }
}
