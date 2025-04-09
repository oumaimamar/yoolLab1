package yool1.ma.authservice.dto;

import lombok.Data;
import yool1.ma.authservice.Enum.Ville;

@Data
public class UserProfileDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Ville ville;
    private String motDePasse;
    private String role;

    // Profile fields
    private String headline;
    private String bio;
    private String photoUrl;
    private String location;


}