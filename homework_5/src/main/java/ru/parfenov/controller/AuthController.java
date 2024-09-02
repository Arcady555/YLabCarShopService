package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.parfenov.dto.PersonAllParamDTO;
import ru.parfenov.dto.PersonDTOMapper;
import ru.parfenov.dto.PersonIdPassDTO;
import ru.parfenov.dto.PersonNamePasContDTO;
import ru.parfenov.model.Person;
import ru.parfenov.security.JwtToken;
import ru.parfenov.security.UserDetailsServiceImpl;
import ru.parfenov.service.PersonService;

import java.util.Optional;

@Slf4j
@RestController
public class AuthController {
    private final PersonService personService;
    private final PersonDTOMapper dtoMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtToken jwtToken;

    @Autowired
    public AuthController(PersonService personService,
                          PersonDTOMapper dtoMapper,
                          AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService,
                          JwtToken jwtToken) {
        this.personService = personService;
        this.dtoMapper = dtoMapper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtToken = jwtToken;
    }

    /**
     * Страница регистрации.
     * Метод обработает HTTP запрос Post.
     * Пользователь вводит свои данные и регистрируется в БД
     *
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

    /**
     * Страница получения токена.
     * Метод обработает HTTP запрос Post.
     * Пользователь вводит свои данные и получает токен, который он вставляет в заголовок в дальнейших запросах.
     * Которые требуют авторизацию
     *
     * @param personDTO сущность Person, обвёрнутая в DTO для подачи в виде Json
     * @return ответ сервера
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody PersonIdPassDTO personDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(personDTO.getId(), personDTO.getPassword()));
        } catch (AuthorizationServiceException e) {
            log.error("Неправильный ID или пароль", e);
            return new ResponseEntity<>("Неправильный ID или пароль", HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(personDTO.getId());
        String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }
}