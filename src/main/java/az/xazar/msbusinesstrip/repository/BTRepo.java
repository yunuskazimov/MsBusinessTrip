package az.xazar.msbusinesstrip.repository;

import az.xazar.msbusinesstrip.entity.BTEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BTRepo extends JpaRepository<BTEntity,Long> {
    Optional<List<BTEntity>> findAllByUserId(Long userId);
}
