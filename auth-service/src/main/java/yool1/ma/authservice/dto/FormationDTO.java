package yool1.ma.authservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FormationDTO {
    private String ecole;
    private String diplome;
    private String domainEtude;
    private Date dateDebut;
    private Date dateFin;
    private String description;
    private Long userId;
}
