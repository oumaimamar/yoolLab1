package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yool1.ma.authservice.entities.Experience;
import yool1.ma.authservice.entities.Formation;

import java.util.List;
import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Long> {

    @Query("SELECT e FROM Formation e WHERE e.user.userId = :userId")
    List<Formation> findByUserId(@Param("userId") Long userId);


}
