//package yool1.ma.authservice.mapper;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//import yool1.ma.authservice.dto.ExperienceDto;
//import yool1.ma.authservice.entities.Experience;
//import yool1.ma.authservice.entities.User;
//
//@Component
//public class ExperienceMapper {
//    private final ModelMapper modelMapper = new ModelMapper();
//
//    public ExperienceDto toDto(User user) {
//        ExperienceDto dto = modelMapper.map(user, ExperienceDto.class);
//
//        if (user.getExperiences() != null) {
//            modelMapper.map(user.getExperiences(), dto);
//        }
//        return dto;
//    }
//}
