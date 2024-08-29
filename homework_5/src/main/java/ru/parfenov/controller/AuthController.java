package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.parfenov.dto.UserAllParamDTO;
import ru.parfenov.dto.UserDTOMapper;
import ru.parfenov.dto.UserNamePasContDTO;
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
     * @param userNamePasContDTO сущность Person, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserAllParamDTO> signUp(@RequestBody UserNamePasContDTO userNamePasContDTO) {
        Optional<Person> userOptional =
                personService.createByReg(
                        userNamePasContDTO.getName(),
                        userNamePasContDTO.getPassword(),
                        userNamePasContDTO.getContactInfo()
                );
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}