package yool1.ma.authservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yool1.ma.authservice.Enum.Niveau;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Langue{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String langue;
    private Niveau niveau;
}
