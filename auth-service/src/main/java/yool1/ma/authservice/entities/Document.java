package yool1.ma.authservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yool1.ma.authservice.Enum.TypeDoc;

import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Document {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private TypeDoc typeDoc;
    private Date dateAjout;
    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}