package ru.parfenov.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.parfenov.enums.PersonRole;
import ru.parfenov.model.Person;
import ru.parfenov.repository.PersonRepository;
import ru.parfenov.service.PersonService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static ru.parfenov.utility.Utility.getIntFromString;

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
                new Person(0, PersonRole.CLIENT, name, passwordEncoder.encode(password), contactInfo, 0)
                )
        );
    }

    @Override
    public Optional<Person> createByAdmin(
            int id, String roleStr, String name, String password, String contactInfo, int buysAmount
    ) {
        PersonRole role = getPersonRoleFromString(roleStr);
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
        PersonRole role = getPersonRoleFromString(roleStr);
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
        PersonRole role = getPersonRoleFromString(roleStr);
        int buysAmount = getIntFromString(buysAmountStr);
        return repo.findByParameters(role, name, contactInfo, buysAmount);
    }

    private PersonRole getPersonRoleFromString(String roleStr) {
        return "client".equals(roleStr) ? PersonRole.CLIENT :
                ("manager".equals(roleStr) ? PersonRole.MANAGER :
                        ("admin".equals(roleStr) ? PersonRole.ADMIN :
                                null));
    }
}