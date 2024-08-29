package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.parfenov.dto.UserAllParamDTO;
import ru.parfenov.dto.UserDTOMapper;
import ru.parfenov.model.Person;
import ru.parfenov.service.PersonService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final PersonService personService;
    private final UserDTOMapper dtoMapper;

    @Autowired
    public UserController(PersonService personService, UserDTOMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Страница вывода юзера по введённому id
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param userId ID юзера
     * @return ответ сервера
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAllParamDTO> viewUser(@PathVariable int userId) {
        Optional<Person> userOptional = personService.findById(userId);
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Страница позволяет провести поиск юзеров по нужным параметрам, можно указывать не все
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param role        роль юзера
     * @param name        имя юзера
     * @param contactInfo контактная информация
     * @param buysAmount  число покупок машин
     * @return ответ сервера
     */
    @GetMapping("/find-by_parameters")
    public ResponseEntity<List<UserAllParamDTO>> findByParam(
            @RequestParam String role,
            @RequestParam String name,
            @RequestParam String contactInfo,
            @RequestParam String buysAmount
    ) {
        List<Person> personList = personService.findByParameters(role, name, contactInfo, buysAmount);
        if (!personList.isEmpty()) {
            List<UserAllParamDTO> userListDTO = dtoMapper.toUserAllParamListDTO(personList);
            return new ResponseEntity<>(userListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Страница обновления информации о юзере
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param userAllParamDTO ID юзера
     * @return ответ сервера
     */
    @PostMapping("/update")
    public ResponseEntity<UserAllParamDTO> update(@RequestBody UserAllParamDTO userAllParamDTO) {
        boolean isUserUpdated = personService.update(
                userAllParamDTO.getId(),
                userAllParamDTO.getRole(),
                userAllParamDTO.getName(),
                userAllParamDTO.getPassword(),
                userAllParamDTO.getContactInfo(),
                userAllParamDTO.getBuysAmount()
        );
        if (isUserUpdated) {
            Optional<Person> userOptional = personService.findById(userAllParamDTO.getId());
            return userOptional
                    .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Страница удаления карточки юзера
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param userId ID юзера
     * @return ответ сервера
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable int userId) {
        boolean isUserDeleted = personService.delete(userId);
        return isUserDeleted ?
                new ResponseEntity<>("Person is deleted", HttpStatus.OK) :
                new ResponseEntity<>("Person is not deleted", HttpStatus.BAD_REQUEST);
    }

    /**
     * Страница, где админ может сам создать любого юзера и с нужным профилем
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param userAllParamDTO сущность Person, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/create")
    public ResponseEntity<UserAllParamDTO> create(@RequestBody UserAllParamDTO userAllParamDTO) {
        Optional<Person> userOptional = personService.createByAdmin(
                userAllParamDTO.getId(),
                userAllParamDTO.getRole(),
                userAllParamDTO.getName(),
                userAllParamDTO.getPassword(),
                userAllParamDTO.getContactInfo(),
                userAllParamDTO.getBuysAmount()
        );
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Страница вывода списка всех юзеров
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @return ответ сервера
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserAllParamDTO>> findAll() {
        List<Person> personList = personService.findAll();
        if (!personList.isEmpty()) {
            List<UserAllParamDTO> userListDTO = dtoMapper.toUserAllParamListDTO(personList);
            return new ResponseEntity<>(userListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}