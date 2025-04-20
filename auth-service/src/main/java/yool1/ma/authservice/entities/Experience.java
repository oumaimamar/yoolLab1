package yool1.ma.authservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yool1.ma.authservice.Enum.TypeEmploi;
import yool1.ma.authservice.Enum.Ville;

import java.util.Date;

@Entity
@Data  @AllArgsConstructor @NoArgsConstructor
public class Experience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String post;
    private TypeEmploi typeEmploi;
    private String entreprise;

    private Date dateDebut;
    private Date dateFin;
    private Ville ville;
    private String description;

    @ManyToOne
    private User user;
}
