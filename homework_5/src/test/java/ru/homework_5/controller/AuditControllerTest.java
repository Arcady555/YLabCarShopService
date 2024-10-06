package ru.homework_5.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.homework_5.Main;
import ru.homework_5.model.LineInLog;
import ru.homework_5.service.LogService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Main.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class AuditControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private static LogService logService;
    private static LineInLog lineInLog1;
    private static LineInLog lineInLog2;
    private static List<LineInLog> lineInLogList;

    @BeforeAll
    public static void init() throws Exception {
        lineInLog1 =
                new LineInLog(
                        1,
                        LocalDateTime.of(2024, 1, 10, 12, 20, 9),
                        "2",
                        "this action"
                );
        lineInLog2 =
                new LineInLog(
                        2,
                        LocalDateTime.of(2024, 1, 11, 12, 20, 9),
                        "2",
                        "other action"
                );
        lineInLogList = List.of(lineInLog1, lineInLog2);
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    @DisplayName("Метод findByParameters")
    public void find_by_parameters_successfully() throws Exception {
        when(logService.findByParameters("2", "action", "2024-01-01T01:01:01", ""))
                .thenReturn(lineInLogList);
        mockMvc.perform(get(
                        "/audit/find-by-parameters?userId=2&&action=action" +
                                "&&dateTimeFrom=2024-01-01T01:01:01&&dateTimeTo="
                )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(lineInLogList)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}