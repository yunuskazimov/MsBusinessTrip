package az.xazar.msbusinesstrip.util;

import az.xazar.msbusinesstrip.dao.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.dao.repository.BusinessTripRepo;
import az.xazar.msbusinesstrip.exception.BusinessTripNotFoundException;
import az.xazar.msbusinesstrip.exception.ErrorCodes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class BusinessTripUtil {
    private final BusinessTripRepo repo;

    public BusinessTripUtil(BusinessTripRepo repo) {
        this.repo = repo;
    }

    public BusinessTripEntity findBusinessTrip(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new BusinessTripNotFoundException(ErrorCodes.NOT_FOUND));
    }

    public Page<BusinessTripEntity> findBusinessTripByUserId(Long userId, Pageable pageable) {
        Page<BusinessTripEntity> entityList = repo.findAllByUserId(userId, pageable).get();
        if (entityList.isEmpty()) {
            throw new BusinessTripNotFoundException(ErrorCodes.NOT_FOUND);
        } else {
            return entityList;
        }
    }
}
