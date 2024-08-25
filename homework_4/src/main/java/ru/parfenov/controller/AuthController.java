package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.parfenov.dto.UserDTOMapper;
import ru.parfenov.dto.UserIdNameRoleDTO;
import ru.parfenov.dto.UserIdPassDTO;
import ru.parfenov.dto.UserNamePasContDTO;
import ru.parfenov.model.User;
import ru.parfenov.service.UserService;

import java.util.Optional;

@Slf4j
@RestController
public class AuthController {
    private final UserService userService;
    private final UserDTOMapper dtoMapper;

    @Autowired
    public AuthController(UserService userService, UserDTOMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserIdNameRoleDTO> signUp(@RequestBody UserNamePasContDTO userNamePasContDTO) {
        Optional<User> userOptional =
                userService.createByReg(
                        userNamePasContDTO.getName(),
                        userNamePasContDTO.getPassword(),
                        userNamePasContDTO.getContactInfo()
                );
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserIdNameRoleDTO(user), HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserIdNameRoleDTO> signIn(@RequestBody UserIdPassDTO userIdPassDTO) {
        Optional<User> userOptional = userService.findByIdAndPassword(userIdPassDTO.getId(), userIdPassDTO.getPassword());
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserIdNameRoleDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/sign-out")
    public ResponseEntity<String> signOut() {
        return new ResponseEntity<>("You're out.", HttpStatus.OK);
    }
}