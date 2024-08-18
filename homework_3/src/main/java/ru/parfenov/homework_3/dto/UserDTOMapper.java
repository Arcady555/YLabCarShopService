package ru.parfenov.homework_3.dto;

import org.mapstruct.Mapping;
import ru.parfenov.homework_3.model.User;

public interface UserDTOMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    UserFoRegDTO toUserDtoFoReg(User source);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    User toUser(UserFoRegDTO destination);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    @Mapping(target = "buysAmount", source = "buysAmount")
    UserForAdminDTO toUserForAdminDTO(User source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    @Mapping(target = "buysAmount", source = "buysAmount")
    User toUser(UserForAdminDTO destination);
}
