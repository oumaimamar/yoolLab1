package yool1.ma.authservice.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
public class Certification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String organisation;
    private Date dateDebut;
    private Date dateFin;

    private String description;


    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}
