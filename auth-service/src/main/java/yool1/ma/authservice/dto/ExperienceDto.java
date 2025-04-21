package yool1.ma.authservice.dto;

import lombok.Data;
import yool1.ma.authservice.Enum.TypeEmploi;
import yool1.ma.authservice.Enum.Ville;

import java.util.Date;

@Data
public class ExperienceDto {
    private String post;
    private TypeEmploi typeEmploi;
    private String entreprise;
    private Date dateDebut;
    private Date dateFin;
    private Ville ville;
    private String description;
    private Long userId; // To associate the experience with a user
}
