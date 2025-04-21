package yool1.ma.authservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import yool1.ma.authservice.Enum.Ville;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder @ToString

@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Ville ville;

    @Column(nullable = false)
    private LocalDate dateInscription;

    @Column(nullable = false)
    private String motDePasse;

    @Column(name = "roles")
    private String role;


    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Profile profile;




    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Document> documents;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();
}
