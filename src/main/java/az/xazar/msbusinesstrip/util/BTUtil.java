package az.xazar.msbusinesstrip.util;

import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.error.ErrorCodes;
import az.xazar.msbusinesstrip.exception.BusinessTripNotFoundException;
import az.xazar.msbusinesstrip.repository.BusinessTripRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BTUtil {
    private final BusinessTripRepo repo;

    public BTUtil(BusinessTripRepo repo) {
        this.repo = repo;
    }

    public BusinessTripEntity findBT(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new BusinessTripNotFoundException(ErrorCodes.NOT_FOUND));
    }

    public List<BusinessTripEntity> findBTByUserId(Long userId) {
        return repo.findAllByUserId(userId)
                .orElseThrow(() ->
                        new BusinessTripNotFoundException(ErrorCodes.NOT_FOUND));
    }
}
