package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.parfenov.dto.PersonAllParamDTO;
import ru.parfenov.dto.UserDTOMapper;
import ru.parfenov.dto.PersonNamePasContDTO;
import ru.parfenov.model.Person;
import ru.parfenov.service.PersonService;

import java.util.Optional;

@Slf4j
@RestController
public class AuthController {
    private final PersonService personService;
    private final UserDTOMapper dtoMapper;

    @Autowired
    public AuthController(PersonService personService, UserDTOMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Страница регистрации.
     * Метод обработает HTTP запрос Post.
     * Пользователь вводит свои данные и регистрируется в БД
     * @param personNamePasContDTO сущность Person, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/sign-up")
    public ResponseEntity<PersonAllParamDTO> signUp(@RequestBody PersonNamePasContDTO personNamePasContDTO) {
        Optional<Person> userOptional =
                personService.createByReg(
                        personNamePasContDTO.getName(),
                        personNamePasContDTO.getPassword(),
                        personNamePasContDTO.getContactInfo()
                );
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}