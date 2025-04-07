package yool1.ma.authservice.dto;

import lombok.Data;
import yool1.ma.authservice.entities.Ville;

@Data
public class UserDTO {

    // DTO for user creation
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private Ville ville;
        private String motDePasse;
}
