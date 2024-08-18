package ru.parfenov.homework_3.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.parfenov.homework_3.model.User;

@Mapper
public interface UserDTOMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    UserNamePasContDTO toUserDtoFoReg(User source);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    User toUser(UserNamePasContDTO destination);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    @Mapping(target = "buysAmount", source = "buysAmount")
    UserAllParamDTO toUserAllParamDTO(User source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "contactInfo", source = "contactInfo")
    @Mapping(target = "buysAmount", source = "buysAmount")
    User toUser(UserAllParamDTO destination);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "name", source = "name")
    UserIdNameRoleDTO toUserIdNameRoleDTO(User source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "name", source = "name")
    User toUser(UserIdNameRoleDTO destination);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    UserIdPassDTO toUserIdPassDTO(User source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    User toUser(UserIdPassDTO destination);
}
