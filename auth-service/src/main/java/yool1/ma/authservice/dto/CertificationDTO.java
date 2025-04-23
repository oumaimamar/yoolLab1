package yool1.ma.authservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CertificationDTO {
    private String title;
    private String organisation;
    private Date dateDebut;
    private Date dateFin;
    private String description;
    private Long userId;

}
