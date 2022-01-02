package az.xazar.msbusinesstrip.util;

import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.error.ErrorCodes;
import az.xazar.msbusinesstrip.exception.BusinessTripNotFoundException;
import az.xazar.msbusinesstrip.repository.BusinessTripRepo;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<BusinessTripEntity> findBusinessTripByUserId(Long userId) {
        return repo.findAllByUserId(userId)
                .orElseThrow(() ->
                        new BusinessTripNotFoundException(ErrorCodes.NOT_FOUND));
    }
}
