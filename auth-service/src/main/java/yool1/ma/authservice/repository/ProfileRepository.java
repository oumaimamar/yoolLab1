package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yool1.ma.authservice.entities.Profile;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
