package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yool1.ma.authservice.entities.Experience;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {


    @Query("SELECT e FROM Experience e WHERE e.user.userId = :userId")
    List<Experience> findByUserId(@Param("userId") Long userId);
}
