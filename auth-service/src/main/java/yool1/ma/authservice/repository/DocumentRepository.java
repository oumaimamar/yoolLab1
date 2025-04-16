package yool1.ma.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yool1.ma.authservice.entities.Document;

public interface DocumentRepository  extends JpaRepository<Document, Long> {
}
