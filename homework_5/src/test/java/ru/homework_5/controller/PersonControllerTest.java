package ru.homework_5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.homework_5.Main;
import ru.homework_5.dto.PersonAllParamDTO;
import ru.homework_5.dto.PersonDTOMapper;
import ru.homework_5.dto.PersonDTOMapperImpl;
import ru.homework_5.model.Person;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static final PersonAllParamDTO personAllParamDTO1 = new PersonAllParamDTO();
    private static final PersonAllParamDTO personAllParamDTO2 = new PersonAllParamDTO();
    private static Person person1 = new Person();
    private static Person person2 = new Person();
    private static List<Person> personList;
    private static List<PersonAllParamDTO> listAllParamDTO;

    @BeforeAll
    public static void init() throws Exception {
        PersonDTOMapper personDTOMapper = new PersonDTOMapperImpl();
        personAllParamDTO1.setId(2);
        personAllParamDTO1.setRole("CLIENT");
        personAllParamDTO1.setName("Arcady");
        personAllParamDTO1.setPassword("password");
        personAllParamDTO1.setContactInfo("contact");
        personAllParamDTO1.setBuysAmount(0);
        person1 = personDTOMapper.toUserAllParam(personAllParamDTO1);

        personAllParamDTO1.setId(3);
        personAllParamDTO1.setRole("CLIENT");
        personAllParamDTO1.setName("notArcady");
        personAllParamDTO1.setPassword("other password");
        personAllParamDTO1.setContactInfo("info");
        personAllParamDTO1.setBuysAmount(0);
        person2 = personDTOMapper.toUserAllParam(personAllParamDTO1);

        personList = List.of(person1, person2);
        listAllParamDTO = personDTOMapper.toUserAllParamListDTO(personList);
    }


    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод findById")
    public void test_find_user_by_id() throws Exception {
        mockMvc.perform(get("/users/user/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personAllParamDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод create")
    public void create_user1_successfully() throws Exception {
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personAllParamDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод create")
    public void create_user2_successfully() throws Exception {
        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personAllParamDTO1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод findAll")
    public void find_all_successfully() throws Exception {
        mockMvc.perform(get(
                        "/users/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listAllParamDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод findByParameters")
    public void find_by_parameters_successfully() throws Exception {
        mockMvc.perform(get(
                        "/users/find-by-parameters?" +
                                "role=CLIENT&&name=&&contactInfo=&&buysAmount=0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listAllParamDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод delete")
    public void delete_user_successfully() throws Exception {
        mockMvc.perform(delete("/users/delete/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("User is deleted"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}