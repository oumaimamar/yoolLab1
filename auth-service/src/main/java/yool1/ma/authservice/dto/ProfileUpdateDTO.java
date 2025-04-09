package yool1.ma.authservice.dto;

import lombok.Data;

@Data
public class ProfileUpdateDTO {
    private String headline;
    private String bio;
    private String photoUrl;
    private String location;

}