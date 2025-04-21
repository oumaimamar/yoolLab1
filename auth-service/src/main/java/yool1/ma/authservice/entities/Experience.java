package yool1.ma.authservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne @JoinColumn(name = "user_id")
    @JsonIgnore  // Add this to break the cycle
    private User user;
}
