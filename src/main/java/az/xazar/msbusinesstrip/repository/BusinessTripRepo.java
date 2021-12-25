package az.xazar.msbusinesstrip.repository;

import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessTripRepo extends JpaRepository<BusinessTripEntity, Long> {
    Optional<List<BusinessTripEntity>> findAllByUserId(Long userId);
}
