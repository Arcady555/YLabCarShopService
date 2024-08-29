package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
    UserNamePasContDTO toUserDtoFoReg(Person source);

    /**
     * Перевод сущности в DTO, со всеми исходными полями, переведёнными в int и String.
     * (все поля становятся или int или String)
     * Применяется при распечатке данных юзера
     * @param source Person - сущность из блока ru/parfenov/homework_3/model
     * @return DTO объект
     */
    UserAllParamDTO toUserAllParamDTO(Person source);

    /**
     * Преобразование каждого элемента в списке по методу toUserAllParamDTO()
     *
     * @param source список сущностей Person под преобразование
     * @return список полученных элементов
     */
    List<UserAllParamDTO> toUserAllParamListDTO(List<Person> source);
}