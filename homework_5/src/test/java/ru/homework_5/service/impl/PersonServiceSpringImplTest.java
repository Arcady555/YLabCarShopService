package ru.homework_5.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.homework_5.enums.Role;
import ru.homework_5.model.Person;
import ru.homework_5.repository.PersonRepository;
import ru.homework_5.service.PersonService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonServiceSpringImplTest {
    PersonRepository repo = mock(PersonRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    PersonService personService = new PersonServiceSpringImpl(repo, passwordEncoder);

    @Test
    void createByReg() {
        Person person = new Person(2, Role.CLIENT, "Arcady", "password", "contact", 0);
        when(repo.save(any(Person.class))).thenReturn(person);

        Optional<Person> result = personService.createByReg("Arcady", "password", "contact");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(person, result.get());
    }

    @Test
    void createByAdmin() {
        Person person = new Person(2, Role.MANAGER, "Arcady", "password", "contact", 0);
        when(repo.save(any(Person.class))).thenReturn(person);

        Optional<Person> result = personService.createByAdmin(0, "MANAGER", "Arcady", "password", "contact", 0);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(person, result.get());
    }

    @Test
    void findById() {
        Person person = new Person(2, Role.CLIENT, "Arcady", "password", "contact", 0);
        when(repo.findById(2)).thenReturn(Optional.of(person));

        Optional<Person> result = personService.findById(2);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(person, result.get());
    }

    @Test
    void findByIdAndPassword() {
        Person person = new Person(2, Role.CLIENT, "Arcady", "password", "contact", 0);
        when(repo.findByIdAndPassword(2, "password")).thenReturn(person);

        Optional<Person> result = personService.findByIdAndPassword(2, "password");

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(person, result.get());
    }

}