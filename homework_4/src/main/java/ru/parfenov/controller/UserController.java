package ru.parfenov.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.parfenov.dto.UserAllParamDTO;
import ru.parfenov.dto.UserDTOMapper;
import ru.parfenov.model.User;
import ru.parfenov.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDTOMapper dtoMapper;

    @Autowired
    public UserController(UserService userService, UserDTOMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserAllParamDTO> viewUser(@PathVariable int userId) {
        Optional<User> userOptional = userService.findById(userId);
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/find-by_parameters")
    public ResponseEntity<List<UserAllParamDTO>> findByParam(
            @RequestParam String role,
            @RequestParam String name,
            @RequestParam String contactInfo,
            @RequestParam String buysAmount
    ) {
        List<User> userList = userService.findByParameters(role, name, contactInfo, buysAmount);
        if (!userList.isEmpty()) {
            List<UserAllParamDTO> userListDTO = new ArrayList<>();
            for (User user : userList) {
                userListDTO.add(dtoMapper.toUserAllParamDTO(user));
            }
            return new ResponseEntity<>(userListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<UserAllParamDTO> update(@RequestBody UserAllParamDTO userDTO) {
        boolean isUserUpdated = userService.update(
                userDTO.getId(),
                userDTO.getRole(),
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getContactInfo(),
                userDTO.getBuysAmount()
        );
        if (isUserUpdated) {
            Optional<User> userOptional = userService.findById(userDTO.getId());
            return userOptional
                    .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable int userId) {
        boolean isUserDeleted = userService.delete(userId);
        return isUserDeleted ?
                new ResponseEntity<>("User is deleted", HttpStatus.OK) :
                new ResponseEntity<>("User is not deleted", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("create")
    public ResponseEntity<UserAllParamDTO> create(@RequestBody UserAllParamDTO userDTO) {
        Optional<User> userOptional = userService.createByAdmin(
                userDTO.getId(),
                userDTO.getRole(),
                userDTO.getName(),
                userDTO.getPassword(),
                userDTO.getContactInfo(),
                userDTO.getBuysAmount()
        );
        return userOptional
                .map(user -> new ResponseEntity<>(dtoMapper.toUserAllParamDTO(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("all")
    public ResponseEntity<List<UserAllParamDTO>> findAll() {
        List<User> userList = userService.findAll();
        if (!userList.isEmpty()) {
            List<UserAllParamDTO> userListDTO = new ArrayList<>();
            for (User user : userList) {
                userListDTO.add(dtoMapper.toUserAllParamDTO(user));
            }
            return new ResponseEntity<>(userListDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}