package ru.homework_5.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.homework_5.service.PersonService;
import ru.homework_5.utility.Utility;
import ru.homework_5.enums.Role;
import ru.homework_5.model.Person;
import ru.homework_5.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonServiceSpringImpl implements PersonService {
    private final PersonRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonServiceSpringImpl(PersonRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Person> createByReg(String name, String password, String contactInfo) {
        return Optional.of(repo.save(
                new Person(0, Role.CLIENT, name, passwordEncoder.encode(password), contactInfo, 0)
                )
        );
    }

    @Override
    public Optional<Person> createByAdmin(
            int id, String roleStr, String name, String password, String contactInfo, int buysAmount
    ) {
        Role role = getPersonRoleFromString(roleStr);
        return Optional.of(repo.save(
                new Person(0, role, name, passwordEncoder.encode(password), contactInfo, buysAmount)
                )
        );
    }

    @Override
    public Optional<Person> findById(int userId) {
        return repo.findById(userId);
    }

    @Override
    public Optional<Person> findByIdAndPassword(int userId, String password) {
        return Optional.ofNullable(repo.findByIdAndPassword(userId, password));
    }

    @Override
    public boolean update(int userId, String roleStr, String name, String password, String contactInfo, int buysAmount) {
        Role role = getPersonRoleFromString(roleStr);
        Person person = new Person(userId, role, name, password, contactInfo, buysAmount);
        repo.save(person);
        return !repo.existsById(userId);
    }

    @Override
    public boolean delete(int userId) {
        Optional<Person> person = findById(userId);
        person.ifPresent(repo::delete);
        return !repo.existsById(userId);
    }

    @Override
    public List<Person> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Person> findByParameters(String roleStr, String name, String contactInfo, String buysAmountStr) {
        int buysAmount = Utility.getIntFromString(buysAmountStr);
        Role role = getPersonRoleFromString(roleStr);
        return repo.findByParameters(role, name, contactInfo, buysAmount);
    }

    private Role getPersonRoleFromString(String roleStr) {
        return "CLIENT".equals(roleStr) ? Role.CLIENT :
                ("MANAGER".equals(roleStr) ? Role.MANAGER :
                        ("ADMIN".equals(roleStr) ? Role.ADMIN :
                                null));
    }
}