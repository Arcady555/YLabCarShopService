package ru.parfenov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.parfenov.model.LineInLog;
import ru.parfenov.service.LogService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuditController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class AuditControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LogService logService;

    @WithMockUser
    @Test
    @DisplayName("Пока костыль - Метод findByParameters")
    public void find_by_parameters_successfully() throws Exception {
        List<LineInLog> list = insertList();
        when(logService.findByParameters(
                        "1",
                        "action",
                        "2024-09-01T16:05",
                        "2024-09-03T16:05"
                )
        )
                .thenReturn(list);
        mockMvc.perform(get(
                        "/audit/find-by-parameters" +
                                "?userId=1&action=action&dateTimeFrom=2024-09-01T16:05&dateTimeTo=2024-09-03T16:05")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());

    }

    private List<LineInLog> insertList() {
        LineInLog log1 = new LineInLog(
                1, LocalDateTime.of(2024, 9, 2, 16, 5), "1", "action");
        LineInLog log2 = new LineInLog(
                2, LocalDateTime.of(2024, 9, 2, 18, 5), "1", "action");
        return List.of(log1, log2);
    }
}