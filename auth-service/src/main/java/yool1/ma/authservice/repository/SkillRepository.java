package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yool1.ma.authservice.entities.Certification;
import yool1.ma.authservice.entities.Skill;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s WHERE s.user.userId = :userId")
    List<Skill> findByUserId(@Param("userId") Long userId);
}
