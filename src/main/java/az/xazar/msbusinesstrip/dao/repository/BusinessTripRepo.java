package az.xazar.msbusinesstrip.dao.repository;

import az.xazar.msbusinesstrip.dao.entity.BusinessTripEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessTripRepo extends PagingAndSortingRepository<BusinessTripEntity, Long> {
    Optional<Page<BusinessTripEntity>> findAllByUserId(Long userId, Pageable pageable);

    Page<BusinessTripEntity> findByDeletedFalse(Pageable pageable);

}
