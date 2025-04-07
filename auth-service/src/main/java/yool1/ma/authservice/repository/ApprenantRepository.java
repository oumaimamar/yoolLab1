package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yool1.ma.authservice.entities.Apprenant;

public interface ApprenantRepository extends JpaRepository<Apprenant, Long> {
}
