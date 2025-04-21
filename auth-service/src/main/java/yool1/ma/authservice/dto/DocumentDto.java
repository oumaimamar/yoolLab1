package yool1.ma.authservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import yool1.ma.authservice.Enum.TypeDoc;
import yool1.ma.authservice.entities.User;

import java.util.Date;
@Data
public class DocumentDto {
    private String titre;
    private TypeDoc typeDoc;
    private Date dateAjout;
    private MultipartFile filePath;

    private User userId;

}