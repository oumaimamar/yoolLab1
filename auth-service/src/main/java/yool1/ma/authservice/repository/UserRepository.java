package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yool1.ma.authservice.entities.User;



public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
