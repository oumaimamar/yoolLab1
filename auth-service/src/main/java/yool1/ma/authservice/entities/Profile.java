package yool1.ma.authservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder @ToString
@Table(name = "profiles")
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headline;
    private String bio;
    private String photoUrl;
    private String location;

    @OneToOne(cascade = CascadeType.ALL) // Add cascade
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore  // Add this to break the cycle
    private User user;
}
