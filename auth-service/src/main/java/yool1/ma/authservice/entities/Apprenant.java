package yool1.ma.authservice.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import yool1.ma.authservice.Enum.Niveau;

import java.util.Date;


@AllArgsConstructor @NoArgsConstructor @Data @SuperBuilder
@ToString
@Entity
@Table(name = "apprenants")
@PrimaryKeyJoinColumn(name = "user_id")

public class Apprenant extends User {

    private Niveau niveau;
    private String formation;
    private Date dateDebut;

}
