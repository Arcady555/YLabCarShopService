package ru.parfenov.dto;

import org.mapstruct.Mapper;
import ru.parfenov.model.Person;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonDTOMapper {
    PersonNamePasContDTO toUserDtoFoReg(Person source);

    /**
     * Перевод DTO в сущность
     * @param destination DTO
     * @return Person - сущность из блока ru/parfenov/homework_3/model
     */
    Person toPerson(PersonIdPassDTO destination);

    /**
     * Перевод сущности в DTO, со всеми исходными полями, переведёнными в int и String.
     * (все поля становятся или int или String)
     * Применяется при распечатке данных юзера
     * @param source Person - сущность из блока ru/parfenov/homework_3/model
     * @return DTO объект
     */
    PersonAllParamDTO toUserAllParamDTO(Person source);

    /**
     * Преобразование каждого элемента в списке по методу toUserAllParamDTO()
     *
     * @param source список сущностей Person под преобразование
     * @return список полученных элементов
     */
    List<PersonAllParamDTO> toUserAllParamListDTO(List<Person> source);
}