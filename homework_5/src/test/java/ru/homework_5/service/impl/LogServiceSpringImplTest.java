package ru.homework_5.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.homework_5.model.LineInLog;
import ru.homework_5.repository.LogRepository;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class LogServiceSpringImplTest {
    LogRepository repo = mock(LogRepository.class);
    LogServiceSpringImpl logService = new LogServiceSpringImpl(repo);

    @Test
    @DisplayName("Проверка метода findByParameters")
    void findByParameters() {
        LineInLog log = new LineInLog(
                1,
                LocalDateTime.of(2024, 1,1, 1, 1),
                "1",
                "action"
        );
        when(repo.findByParameters(
                "1",
                "action",
                LocalDateTime.of(2024, 1,1, 1, 1),
                LocalDateTime.of(2024, 2,1, 1, 1)
        )).thenReturn(List.of(log));

        List<LineInLog> result = logService.findByParameters(
                "1", "action", "2024-01-01T01:01", "2024-02-01T01:01"
        );

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(List.of(log), result);
    }
}