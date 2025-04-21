package yool1.ma.authservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import yool1.ma.authservice.Enum.TypeEmploi;
import yool1.ma.authservice.Enum.Ville;

import java.util.Date;

@Entity
@Data  @AllArgsConstructor @NoArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String post;

    @Enumerated(EnumType.STRING)
    private TypeEmploi typeEmploi;

    private String entreprise;
    private Date dateDebut;
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private Ville ville;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
