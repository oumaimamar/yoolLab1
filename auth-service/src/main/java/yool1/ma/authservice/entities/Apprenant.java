package yool1.ma.authservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder

@Table(name = "apprenants")
@PrimaryKeyJoinColumn(name = "user_id")

public class Apprenant extends User {

    private Niveau niveau;
    private String formation;
    private Date dateDebut;

}
