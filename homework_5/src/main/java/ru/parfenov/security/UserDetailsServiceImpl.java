package ru.parfenov.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.parfenov.model.Person;
import ru.parfenov.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static ru.parfenov.utility.Utility.getIntFromString;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PersonRepository persons;

    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        Optional<Person> personOptional = persons.findById(getIntFromString(userIdStr));
        if (personOptional.isEmpty()) {
            throw new UsernameNotFoundException(userIdStr);
        }
        Person person = personOptional.get();
        return new User(
                Integer.toString(person.getId()),
                person.getPassword(),
                List.of(new SimpleGrantedAuthority(person.getRole().toString())));
    }
}