package yool1.ma.authservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import yool1.ma.authservice.dto.UserProfileDTO;
import yool1.ma.authservice.entities.User;

@Component
public class UserMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public UserProfileDTO toDto(User user) {
        UserProfileDTO dto = modelMapper.map(user, UserProfileDTO.class);
        if (user.getProfile() != null) {
            modelMapper.map(user.getProfile(), dto);
        }
        return dto;
    }
}
