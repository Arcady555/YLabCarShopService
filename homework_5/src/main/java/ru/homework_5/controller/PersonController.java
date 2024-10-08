package ru.homework_5.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.homework_5.dto.PersonAllParamDTO;
import ru.homework_5.dto.PersonDTOMapper;
import ru.homework_5.model.Person;
import ru.homework_5.service.PersonService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class PersonController {
    private final PersonService personService;
    private final PersonDTOMapper dtoMapper;

    @Autowired
    public PersonController(PersonService personService, PersonDTOMapper dtoMapper) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Страница, где админ может сам создать любого юзера и с нужным профилем
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param personAllParamDTO сущность Person, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/create")
    public ResponseEntity<PersonAllParamDTO> create(@RequestBody PersonAllParamDTO personAllParamDTO) {
        Optional<Person> userOptional = personService.createByAdmin(
                personAllParamDTO.getId(),
                personAllParamDTO.getRole(),
                personAllParamDTO.getName(),
                personAllParamDTO.getPassword(),
                personAllParamDTO.getContactInfo(),
                personAllParamDTO.getBuysAmount()
        );
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    /**
     * Страница вывода юзера по введённому id
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param userId ID юзера
     * @return ответ сервера
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<PersonAllParamDTO> viewUser(@PathVariable int userId) {
        Optional<Person> userOptional = personService.findById(userId);
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Страница обновления информации о юзере
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param personAllParamDTO ID юзера
     * @return ответ сервера
     */
    @PostMapping("/update")
    public ResponseEntity<PersonAllParamDTO> update(@RequestBody PersonAllParamDTO personAllParamDTO) {
        boolean isUserUpdated = personService.update(
                personAllParamDTO.getId(),
                personAllParamDTO.getRole(),
                personAllParamDTO.getName(),
                personAllParamDTO.getPassword(),
                personAllParamDTO.getContactInfo(),
                personAllParamDTO.getBuysAmount()
        );
        if (isUserUpdated) {
            Optional<Person> userOptional = personService.findById(personAllParamDTO.getId());
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
     * Страница вывода списка всех юзеров
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @return ответ сервера
     */
    @GetMapping("/all")
    public ResponseEntity<List<PersonAllParamDTO>> findAll() {
        List<Person> personList = personService.findAll();
        if (!personList.isEmpty()) {
            List<PersonAllParamDTO> userListDTO = dtoMapper.toUserAllParamListDTO(personList);
            return new ResponseEntity<>(userListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Страница позволяет провести поиск юзеров по нужным параметрам
     * Данный метод, доступный только админу(через фильтр сервлетов),
     *
     * @param role        роль юзера
     * @param name        имя юзера
     * @param contactInfo контактная информация
     * @param buysAmount  число покупок машин
     * @return ответ сервера
     */
    @GetMapping("/find-by_parameters")
    public ResponseEntity<List<PersonAllParamDTO>> findByParam(
            @RequestParam String role,
            @RequestParam String name,
            @RequestParam String contactInfo,
            @RequestParam String buysAmount
    ) {
        List<Person> personList = personService.findByParameters(role, name, contactInfo, buysAmount);
        if (!personList.isEmpty()) {
            List<PersonAllParamDTO> userListDTO = dtoMapper.toUserAllParamListDTO(personList);
            return new ResponseEntity<>(userListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}