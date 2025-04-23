package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yool1.ma.authservice.entities.Certification;
import yool1.ma.authservice.entities.Formation;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    @Query("SELECT e FROM Certification e WHERE e.user.userId = :userId")
    List<Certification> findByUserId(@Param("userId") Long userId);


}
