package yool1.ma.authservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import yool1.ma.authservice.Enum.TypeDoc;

import java.util.Date;
@Data
public class DocumentDto {

    // getters and setters

    private String titre;
    private TypeDoc typeDoc;
    private Date dateAjout;
    private MultipartFile filePath;

}
